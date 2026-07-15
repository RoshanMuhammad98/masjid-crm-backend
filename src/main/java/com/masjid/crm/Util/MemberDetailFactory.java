package com.masjid.crm.Util;

import com.masjid.crm.dto.request.MemberDetailRequest;
import com.masjid.crm.dto.response.MemberDetailListResponse;
import com.masjid.crm.dto.response.MemberDetailResponse;
import com.masjid.crm.entity.FamilyDetail;
import com.masjid.crm.entity.MemberDetail;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class MemberDetailFactory {

    public static MemberDetail buildMemberDetail(MemberDetailRequest request, MemberDetail memberDetail, FamilyDetail familyDetail) {
        if (memberDetail == null) {
            memberDetail = new MemberDetail();
        }
        memberDetail.setName(request.getName());
        memberDetail.setMartialStatus(request.getMartialStatus());
        memberDetail.setGender(request.getGender());
        memberDetail.setAge(request.getAge());
        memberDetail.setEducationQualification(request.getEducationQualification());
        memberDetail.setOccupation(request.getOccupation());
        memberDetail.setPhoneNumber(request.getPhoneNumber());
        memberDetail.setAlternativeNumber(request.getAlternativeNumber());
        memberDetail.setBloodGroup(request.getBloodGroup());
        memberDetail.setFamilyDetail(familyDetail);
        memberDetail.setMedicalCondition(request.getMedicalCondition());
        memberDetail.setHasMedicalIssue(request.getHasMedicalIssue());
        memberDetail.setHasDisability(request.getHasDisability());
        memberDetail.setDisabilityNotes(request.getDisabilityNotes());
        memberDetail.setIsStudent(request.getIsStudent());
        memberDetail.setDateOfDivorce(request.getDateOfDivorce());
        memberDetail.setDivorcedFromName(request.getDivorcedFromName());
        memberDetail.setDivorceNotes(request.getDivorceNotes());
        if (request.getIsHead() != null) {
            memberDetail.setIsHead(request.getIsHead());
        }

        applyOccupationStudentRule(memberDetail);

        return memberDetail;
    }

    private static void applyOccupationStudentRule(MemberDetail member) {
        String occ = member.getOccupation();
        boolean hasRealJob = occ != null && !occ.trim().isEmpty()
                && !occ.trim().equalsIgnoreCase("student");

        if (hasRealJob) {
            member.setIsStudent(false);
        } else if (occ != null && occ.trim().equalsIgnoreCase("student")) {
            member.setIsStudent(true);
        }
    }

    public static MemberDetailResponse buildMemberDetailResponse(MemberDetail memberDetail) {
        MemberDetailResponse response = new MemberDetailResponse();
        response.setId(memberDetail.getId());
        response.setName(memberDetail.getName());
        response.setMartialStatus(memberDetail.getMartialStatus());
        response.setGender(memberDetail.getGender());
        response.setAge(memberDetail.getAge());
        response.setEducationQualification(memberDetail.getEducationQualification());
        response.setOccupation(memberDetail.getOccupation());
        response.setPhoneNumber(memberDetail.getPhoneNumber());
        response.setAlternativeNumber(memberDetail.getAlternativeNumber());
        response.setBloodGroup(memberDetail.getBloodGroup());
        if (memberDetail.getFamilyDetail() != null) {
            response.setFamilyId(memberDetail.getFamilyDetail().getId());
            response.setHouseholdName(memberDetail.getFamilyDetail().getHouseholdName());
        }
        response.setMedicalCondition(memberDetail.getMedicalCondition());
        response.setHasMedicalIssue(memberDetail.getHasMedicalIssue());
        response.setHasDisability(memberDetail.getHasDisability());
        response.setDisabilityNotes(memberDetail.getDisabilityNotes());
        response.setIsStudent(memberDetail.getIsStudent());
        response.setDateOfDivorce(memberDetail.getDateOfDivorce());
        response.setDivorcedFromName(memberDetail.getDivorcedFromName());
        response.setDivorceNotes(memberDetail.getDivorceNotes());
        response.setIsHead(memberDetail.getIsHead());
        return response;
    }

    public static MemberDetailListResponse buildMemberDetailListResponse(Page<MemberDetail> memberDetails, Long count) {
        List<MemberDetailResponse> responses = memberDetails.getContent().stream()
                .map(MemberDetailFactory::buildMemberDetailResponse)
                .collect(Collectors.toList());
        return MemberDetailListResponse.builder().memberDetails(responses).count(count)
                .build();
    }

}
