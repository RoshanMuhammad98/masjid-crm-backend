package com.masjid.crm.service;

import com.masjid.crm.Util.MarriageDetailFactory;
import com.masjid.crm.specification.MarriageDetailSpecification;
import com.masjid.crm.dto.request.MarriageDetailRequest;
import com.masjid.crm.dto.response.MarriageDetailListResponse;
import com.masjid.crm.entity.MarriageDetail;
import com.masjid.crm.entity.MemberDetail;
import com.masjid.crm.repository.MarriageRepository;
import com.masjid.crm.repository.MemberDetailsRepository;
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
public class MarriageService {

    @Autowired
    private MarriageRepository marriageRepository;

    @Autowired
    private MemberDetailsRepository memberDetailsRepository;

    public ResponseEntity<MarriageDetail> saveMarriageDetails(MarriageDetailRequest request) {
        MarriageDetail savedMarriageDetail = saveMarriageDetail(request);
        return ResponseEntity.ok(savedMarriageDetail);
    }

    private MarriageDetail saveMarriageDetail(MarriageDetailRequest request) {
        MarriageDetail marriageDetail = null;

        if (request.getId() != null) {
            marriageDetail = marriageRepository.findById(request.getId())
                    .orElse(new MarriageDetail());
        } else {
            marriageDetail = new MarriageDetail();
        }

        Optional<MemberDetail> memberDetail = memberDetailsRepository.findById(request.getMemberDetailId());

        marriageDetail = MarriageDetailFactory.buildMarriageDetail(request, memberDetail.get(), marriageDetail);
        return marriageRepository.save(marriageDetail);
    }

    public MarriageDetailListResponse filteredMarriageDetails(MarriageDetailRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNo(), request.getPageSize(), Sort.by("id").descending());
        Specification<MarriageDetail> spec = MarriageDetailSpecification.filterMarriages(request);
        Page<MarriageDetail> marriageDetails = marriageRepository.findAll(spec, pageable);
        Long count = marriageDetails.getTotalElements();
        return MarriageDetailFactory.buildMarriageDetailsListResponse(marriageDetails, count);
    }

}