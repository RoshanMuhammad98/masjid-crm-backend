package com.masjid.crm.service;

import com.masjid.crm.Util.MemberDetailFactory;
import com.masjid.crm.dto.request.MemberDetailRequest;
import com.masjid.crm.dto.response.FamilyDetailResponse;
import com.masjid.crm.dto.response.MemberDetailListResponse;
import com.masjid.crm.entity.FamilyDetail;
import com.masjid.crm.entity.MarriageDetail;
import com.masjid.crm.entity.MemberDetail;
import com.masjid.crm.repository.FamilyDetailRepository;
import com.masjid.crm.repository.MemberDetailsRepository;
import com.masjid.crm.specification.MemberDetailSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberDetailsService {

    @Autowired
    private MemberDetailsRepository memberDetailRepository;

    @Autowired
    private FamilyDetailRepository familyDetailRepository;

    public ResponseEntity<MemberDetail> saveMemberDetails(MemberDetailRequest request) {
        MemberDetail saveMemberDetail = saveMemberDetail(request);
        return ResponseEntity.ok(saveMemberDetail);
    }

    private MemberDetail saveMemberDetail(MemberDetailRequest request) {
        MemberDetail memberDetail = null;

        if (request.getFamilyDetailId() != null) {

            Optional<FamilyDetail> familyDetail = familyDetailRepository.findById(request.getFamilyDetailId());
            if (familyDetail.isPresent()) {
                if (request.getId() != null) {
                    memberDetail = memberDetailRepository.findById(request.getId())
                            .orElse(new MemberDetail());
                } else {
                    memberDetail = new MemberDetail();
                }
                memberDetail = MemberDetailFactory.buildMemberDetail(request, memberDetail, familyDetail.get());
                return memberDetailRepository.save(memberDetail);
            }
        }
        return null;
    }

    public MemberDetailListResponse filteredMemberDetails(MemberDetailRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNo(), request.getPageSize(), Sort.by("id").descending());
        Specification<MemberDetail> spec = MemberDetailSpecification.filterMemberDetails(request);
        Page<MemberDetail> memberDetails = memberDetailRepository.findAll(spec, pageable);
        Long count = memberDetails.getTotalElements();
        return MemberDetailFactory.buildMemberDetailListResponse(memberDetails, count);
    }

}
