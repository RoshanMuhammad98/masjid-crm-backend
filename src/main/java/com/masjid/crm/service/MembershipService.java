package com.masjid.crm.service;

import com.masjid.crm.Util.MembershipDetailFactory;
import com.masjid.crm.dto.request.MembershipDetailRequest;
import com.masjid.crm.dto.response.FamilyDetailResponse;
import com.masjid.crm.dto.response.MembershipDetailListResponse;
import com.masjid.crm.entity.FamilyDetail;
import com.masjid.crm.entity.MembershipDetail;
import com.masjid.crm.repository.FamilyDetailRepository;
import com.masjid.crm.repository.MembershipRepository;
import com.masjid.crm.specification.MembershipDetailSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MembershipService {

    @Autowired
    private MembershipRepository membershipRepository;

    @Autowired
    private FamilyDetailRepository familyDetailRepository;

    public ResponseEntity<MembershipDetail> saveMembershipDetails(MembershipDetailRequest request) {
        MembershipDetail saveMembershipDetail = saveMembershipDetail(request);
        return ResponseEntity.ok(saveMembershipDetail);
    }

    private MembershipDetail saveMembershipDetail(MembershipDetailRequest request) {
        MembershipDetail membershipDetail = null;
        Optional<FamilyDetail> familyDetail = null;
        if (request.getFamilyDetailId() != null) {
            familyDetail = familyDetailRepository.findById(request.getFamilyDetailId());
        }
        if (request.getId() != null) {
            membershipDetail = membershipRepository.findById(request.getId())
                    .orElse(new MembershipDetail());
        } else {
            membershipDetail = new MembershipDetail();
        }

        membershipDetail = MembershipDetailFactory.buildMembershipDetail(request, familyDetail.get());
        return membershipRepository.save(membershipDetail);
    }

    public MembershipDetailListResponse filteredMembershipDetails(MembershipDetailRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNo(), request.getPageSize(), Sort.by("id").descending());
        Specification<MembershipDetail> spec = MembershipDetailSpecification.filterMemberships(request);
        Page<MembershipDetail> membershipDetails = membershipRepository.findAll(spec, pageable);
        Long count = membershipDetails.getTotalElements();
        return MembershipDetailFactory.buildMembershipDetailsListResponse(membershipDetails, count);
    }

}
