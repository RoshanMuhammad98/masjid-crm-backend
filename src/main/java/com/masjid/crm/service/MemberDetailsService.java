package com.masjid.crm.service;

import com.masjid.crm.Util.MemberDetailFactory;
import com.masjid.crm.dto.request.MemberDetailRequest;
import com.masjid.crm.dto.response.MemberDetailListResponse;
import com.masjid.crm.entity.FamilyDetail;
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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class MemberDetailsService {

    @Autowired
    private MemberDetailsRepository memberDetailRepository;

    @Autowired
    private FamilyDetailRepository familyDetailRepository;

    public MemberDetail saveMemberDetails(MemberDetailRequest request) {
        if (request.getFamilyDetailId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "familyDetailId is required");
        }

        FamilyDetail family = familyDetailRepository.findById(request.getFamilyDetailId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Family not found: " + request.getFamilyDetailId()));

        MemberDetail memberDetail;
        if (request.getId() != null) {
            memberDetail = memberDetailRepository.findById(request.getId())
                    .orElse(new MemberDetail());
        } else {
            memberDetail = new MemberDetail();
        }
        memberDetail = MemberDetailFactory.buildMemberDetail(request, memberDetail, family);
        return memberDetailRepository.save(memberDetail);
    }

    public MemberDetailListResponse filteredMemberDetails(MemberDetailRequest request) {
        int pageNo = request.getPageNo() == null ? 0 : request.getPageNo();
        int pageSize = request.getPageSize() == null || request.getPageSize() <= 0 ? 20 : request.getPageSize();
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("id").descending());
        Specification<MemberDetail> spec = MemberDetailSpecification.filterMemberDetails(request);
        Page<MemberDetail> memberDetails = memberDetailRepository.findAll(spec, pageable);
        Long count = memberDetails.getTotalElements();
        return MemberDetailFactory.buildMemberDetailListResponse(memberDetails, count);
    }

    public Optional<MemberDetail> findById(Long memberDetailId) {
        return memberDetailRepository.findById(memberDetailId);
    }
}
