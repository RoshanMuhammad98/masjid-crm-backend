package com.masjid.crm.service;

import com.masjid.crm.Util.DeathDetailFactory;
import com.masjid.crm.Util.FamilyDetailFactory;
import com.masjid.crm.Util.MarriageDetailFactory;
import com.masjid.crm.Util.MemberDetailFactory;
import com.masjid.crm.dto.request.FamilyDetailRequest;
import com.masjid.crm.dto.request.HeadMemberRequest;
import com.masjid.crm.dto.request.SavedFamilyDetailRequest;
import com.masjid.crm.dto.response.DeathDetailResponse;
import com.masjid.crm.dto.response.FamilyDetailListResponse;
import com.masjid.crm.dto.response.FamilyOverviewResponse;
import com.masjid.crm.dto.response.MarriageDetailResponse;
import com.masjid.crm.dto.response.MemberDetailResponse;
import com.masjid.crm.entity.DeathDetail;
import com.masjid.crm.entity.FamilyDetail;
import com.masjid.crm.entity.MarriageDetail;
import com.masjid.crm.entity.MemberDetail;
import com.masjid.crm.model.Gender;
import com.masjid.crm.model.MartialStatus;
import com.masjid.crm.repository.DeathDetailRepository;
import com.masjid.crm.repository.FamilyDetailRepository;
import com.masjid.crm.repository.MarriageRepository;
import com.masjid.crm.repository.MemberDetailsRepository;
import com.masjid.crm.specification.FamilyDetailSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FamilyService {

    @Autowired
    private FamilyDetailRepository familyDetailRepository;

    @Autowired
    private MemberDetailsRepository memberDetailsRepository;

    @Autowired
    private MarriageRepository marriageRepository;

    @Autowired
    private DeathDetailRepository deathDetailRepository;

    @Transactional
    public FamilyDetail saveFamilyDetails(SavedFamilyDetailRequest request) {
        boolean isNew = request.getId() == null;
        if (isNew) {
            HeadMemberRequest head = request.getHeadMember();
            if (head == null || head.getName() == null || head.getName().trim().isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "headMember with at least a name is required when creating a new family");
            }
        }

        FamilyDetail saveFamilyDetail = saveFamilyDetail(request);
        if (request.getHeadMember() != null) {
            saveHeadMember(saveFamilyDetail, request.getHeadMember());
        }
        return saveFamilyDetail;
    }

    private FamilyDetail saveFamilyDetail(SavedFamilyDetailRequest request) {

        FamilyDetail familyDetail = null;

        if (request.getId() != null) {
            familyDetail = familyDetailRepository.findById(request.getId())
                    .orElse(new FamilyDetail());
        } else {
            familyDetail = new FamilyDetail();
        }

        familyDetail = FamilyDetailFactory.buildFamilyDetail(request, familyDetail);
        return familyDetailRepository.save(familyDetail);
    }

    private void saveHeadMember(FamilyDetail family, HeadMemberRequest headReq) {
        MemberDetail head = memberDetailsRepository
                .findFirstByFamilyDetailIdAndIsHeadTrue(family.getId())
                .orElseGet(MemberDetail::new);

        head.setName(headReq.getName());
        head.setGender(headReq.getGender());
        head.setMartialStatus(headReq.getMartialStatus());
        head.setAge(headReq.getAge());
        head.setEducationQualification(headReq.getEducationQualification());
        head.setOccupation(headReq.getOccupation());
        head.setPhoneNumber(headReq.getPhoneNumber());
        head.setAlternativeNumber(headReq.getAlternativeNumber());
        head.setBloodGroup(headReq.getBloodGroup());
        head.setMedicalCondition(headReq.getMedicalCondition());
        head.setHasMedicalIssue(headReq.getHasMedicalIssue());
        head.setHasDisability(headReq.getHasDisability());
        head.setDisabilityNotes(headReq.getDisabilityNotes());
        head.setIsStudent(headReq.getIsStudent());

        String occ = headReq.getOccupation();
        if (occ != null && !occ.trim().isEmpty()
                && !occ.trim().equalsIgnoreCase("student")) {
            head.setIsStudent(false);
        } else if (occ != null && occ.trim().equalsIgnoreCase("student")) {
            head.setIsStudent(true);
        }
        head.setDateOfDivorce(headReq.getDateOfDivorce());
        head.setDivorcedFromName(headReq.getDivorcedFromName());
        head.setDivorceNotes(headReq.getDivorceNotes());
        head.setFamilyDetail(family);
        head.setIsHead(true);

        memberDetailsRepository.save(head);
    }

    public FamilyOverviewResponse getFamilyOverview(Long familyId) {
        FamilyDetail family = familyDetailRepository.findById(familyId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Family not found: " + familyId));

        List<MemberDetail> members = memberDetailsRepository.findByFamilyDetailId(familyId);
        List<MarriageDetail> marriages = marriageRepository.findByMemberDetail_FamilyDetail_Id(familyId);
        List<DeathDetail> deaths = deathDetailRepository.findByMemberDetail_FamilyDetail_Id(familyId);

        List<MemberDetailResponse> memberResponses = members.stream()
                .map(MemberDetailFactory::buildMemberDetailResponse)
                .collect(Collectors.toList());

        List<MarriageDetailResponse> marriageResponses = marriages.stream()
                .map(MarriageDetailFactory::toResponse)
                .collect(Collectors.toList());

        List<DeathDetailResponse> deathResponses = deaths.stream()
                .map(DeathDetailFactory::toResponse)
                .collect(Collectors.toList());

        long registered = members.size();
        Long declared = family.getTotalMembersCount();
        long pending = declared == null ? 0 : Math.max(0, declared - registered);

        FamilyOverviewResponse.FamilyStats stats = FamilyOverviewResponse.FamilyStats.builder()
                .totalMembers(registered)
                .marriedCount(countByMartialStatus(members, MartialStatus.MARRIED))
                .unmarriedCount(countByMartialStatus(members, MartialStatus.UNMARRIED))
                .divorcedCount(countByMartialStatus(members, MartialStatus.DIVORCED))
                .deathCount(deaths.size())
                .studentsCount(members.stream().filter(m -> Boolean.TRUE.equals(m.getIsStudent())).count())
                .disabilityCount(members.stream().filter(m -> Boolean.TRUE.equals(m.getHasDisability())).count())
                .disabilityMaleCount(members.stream()
                        .filter(m -> Boolean.TRUE.equals(m.getHasDisability()) && m.getGender() == Gender.MALE)
                        .count())
                .disabilityFemaleCount(members.stream()
                        .filter(m -> Boolean.TRUE.equals(m.getHasDisability()) && m.getGender() == Gender.FEMALE)
                        .count())
                .medicalIssueCount(members.stream()
                        .filter(m -> Boolean.TRUE.equals(m.getHasMedicalIssue())).count())
                .maleCount(members.stream().filter(m -> m.getGender() == Gender.MALE).count())
                .femaleCount(members.stream().filter(m -> m.getGender() == Gender.FEMALE).count())
                .declaredMembersCount(declared)
                .registeredMembersCount(registered)
                .pendingMemberRegistrations(pending)
                .registrationComplete(declared != null && pending == 0)
                .build();

        return FamilyOverviewResponse.builder()
                .family(FamilyDetailFactory.toResponse(family))
                .members(memberResponses)
                .marriages(marriageResponses)
                .deaths(deathResponses)
                .stats(stats)
                .build();
    }

    private long countByMartialStatus(List<MemberDetail> members, MartialStatus status) {
        return members.stream().filter(m -> m.getMartialStatus() == status).count();
    }

    public FamilyDetailListResponse filteredFamilyDetails(FamilyDetailRequest request) {
        int pageNo = request.getPageNo() == null ? 0 : request.getPageNo();
        int pageSize = request.getPageSize() == null || request.getPageSize() <= 0 ? 20 : request.getPageSize();
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("id").descending());
        Specification<FamilyDetail> spec = FamilyDetailSpecification.filterfamilies(request);
        Page<FamilyDetail> familyDetails = familyDetailRepository.findAll(spec, pageable);
        Long count = familyDetails.getTotalElements();
        return FamilyDetailFactory.buildFamilyDetailsListResponse(familyDetails, count);
    }

}
