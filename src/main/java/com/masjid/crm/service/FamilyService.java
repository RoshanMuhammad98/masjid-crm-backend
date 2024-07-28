package com.masjid.crm.service;

import com.masjid.crm.Util.FamilyDetailFactory;
import com.masjid.crm.dto.request.FamilyDetailRequest;
import com.masjid.crm.dto.response.FamilyDetailListResponse;
import com.masjid.crm.entity.FamilyDetail;
import com.masjid.crm.repository.FamilyDetailRepository;
import com.masjid.crm.specification.FamilyDetailSpecification;
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
public class FamilyService {

    @Autowired
    private FamilyDetailRepository familyDetailRepository;

    public ResponseEntity<FamilyDetail> saveFamilyDetails(FamilyDetailRequest request) {
        FamilyDetail saveFamilyDetail = saveFamilyDetail(request);
        return ResponseEntity.ok(saveFamilyDetail);
    }

    private FamilyDetail saveFamilyDetail(FamilyDetailRequest request) {
        Optional<FamilyDetail> existingFamilyDetailOpt = familyDetailRepository.findById(request.getId());

        FamilyDetail familyDetail = existingFamilyDetailOpt.orElseGet(FamilyDetail::new);
        familyDetail = FamilyDetailFactory.buildFamilyDetail(request);
        return familyDetailRepository.save(familyDetail);
    }

    public FamilyDetailListResponse filteredFamilyDetails(FamilyDetailRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNo(), request.getPageSize(), Sort.by("id").descending());
        Specification<FamilyDetail> spec = FamilyDetailSpecification.filterfamilies(request);
        Page<FamilyDetail> familyDetails = familyDetailRepository.findAll(spec, pageable);
        Long count = familyDetails.getTotalElements();
        return FamilyDetailFactory.buildFamilyDetailsListResponse(familyDetails, count);
    }

}
