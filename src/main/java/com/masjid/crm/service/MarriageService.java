package com.masjid.crm.service;

import com.masjid.crm.Util.MarriageDetailFactory;
import com.masjid.crm.specification.MarriageDetailSpecification;
import com.masjid.crm.dto.request.MarriageDetailRequest;
import com.masjid.crm.dto.response.MarriageDetailListResponse;
import com.masjid.crm.entity.MarriageDetail;
import com.masjid.crm.entity.MemberDetail;
import com.masjid.crm.model.MartialStatus;
import com.masjid.crm.repository.MarriageRepository;
import com.masjid.crm.repository.MemberDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class MarriageService {

    @Autowired
    private MarriageRepository marriageRepository;

    @Autowired
    private MemberDetailsRepository memberDetailsRepository;

    public MarriageDetail saveMarriageDetails(MarriageDetailRequest request) {
        return saveMarriageDetail(request);
    }

    private MarriageDetail saveMarriageDetail(MarriageDetailRequest request) {
        if (request.getMemberDetailId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "memberDetailId is required");
        }

        MarriageDetail marriageDetail;
        if (request.getId() != null) {
            marriageDetail = marriageRepository.findById(request.getId())
                    .orElse(new MarriageDetail());
        } else {
            marriageDetail = new MarriageDetail();
        }

        MemberDetail memberDetail = memberDetailsRepository.findById(request.getMemberDetailId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Member not found: " + request.getMemberDetailId()));

        marriageDetail = MarriageDetailFactory.buildMarriageDetail(request, memberDetail, marriageDetail);
        MarriageDetail saved = marriageRepository.save(marriageDetail);

        syncMemberMartialStatus(memberDetail);

        return saved;
    }

    private void syncMemberMartialStatus(MemberDetail member) {
        if (member.getMartialStatus() != MartialStatus.MARRIED) {
            member.setMartialStatus(MartialStatus.MARRIED);
            memberDetailsRepository.save(member);
        }
    }

    public MarriageDetailListResponse filteredMarriageDetails(MarriageDetailRequest request) {
        int pageNo = request.getPageNo() == null ? 0 : request.getPageNo();
        int pageSize = request.getPageSize() == null || request.getPageSize() <= 0 ? 20 : request.getPageSize();
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("id").descending());
        Specification<MarriageDetail> spec = MarriageDetailSpecification.filterMarriages(request);
        Page<MarriageDetail> marriageDetails = marriageRepository.findAll(spec, pageable);
        Long count = marriageDetails.getTotalElements();
        return MarriageDetailFactory.buildMarriageDetailsListResponse(marriageDetails, count);
    }

}