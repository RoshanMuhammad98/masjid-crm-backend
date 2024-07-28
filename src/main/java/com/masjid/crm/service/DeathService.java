package com.masjid.crm.service;

import com.masjid.crm.Util.DeathDetailFactory;
import com.masjid.crm.dto.request.DeathDetailRequest;
import com.masjid.crm.dto.response.DeathDetailListResponse;
import com.masjid.crm.dto.response.DeathDetailResponse;
import com.masjid.crm.entity.DeathDetail;
import com.masjid.crm.repository.DeathDetailRepository;
import com.masjid.crm.specification.DeathDetailSpecification;
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
public class DeathService {

    @Autowired
    private DeathDetailRepository deathDetailRepository;

    public ResponseEntity<DeathDetail> saveDeathDetails(DeathDetailRequest request) {
        DeathDetail saveDeathDetail = saveDeathDetail(request);
        return ResponseEntity.ok(saveDeathDetail);
    }

    private DeathDetail saveDeathDetail(DeathDetailRequest request) {

        Optional<DeathDetail> existingDeathDetailOpt = deathDetailRepository.findById(request.getId());
        DeathDetail deathDetail = existingDeathDetailOpt.orElseGet(DeathDetail::new);
        deathDetail = DeathDetailFactory.buildDeathDetail(request);
        return deathDetailRepository.save(deathDetail);
    }

    public DeathDetailListResponse filteredDeathDetails(DeathDetailRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNo(), request.getPageSize(), Sort.by("id").descending());
        Specification<DeathDetail> spec = DeathDetailSpecification.filterDeaths(request);
        Page<DeathDetail> deathDetails = deathDetailRepository.findAll(spec, pageable);
        Long count = deathDetails.getTotalElements();
        return DeathDetailFactory.buildDeathDetailsListResponse(deathDetails, count);
    }

}
