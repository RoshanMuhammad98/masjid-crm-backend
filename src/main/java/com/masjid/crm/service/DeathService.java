package com.masjid.crm.service;

import com.masjid.crm.Util.DeathDetailFactory;
import com.masjid.crm.dto.request.DeathDetailRequest;
import com.masjid.crm.dto.request.SaveDeathDetailRequest;
import com.masjid.crm.dto.response.DeathDetailListResponse;
import com.masjid.crm.entity.DeathDetail;
import com.masjid.crm.entity.MemberDetail;
import com.masjid.crm.repository.DeathDetailRepository;
import com.masjid.crm.specification.DeathDetailSpecification;
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
public class DeathService {

    @Autowired
    private DeathDetailRepository deathDetailRepository;

    @Autowired
    private MemberDetailsService memberDetailsService;

    public DeathDetail saveDeathDetails(SaveDeathDetailRequest request) {
        return saveDeathDetail(request);
    }

    private DeathDetail saveDeathDetail(SaveDeathDetailRequest request) {

        if (request.getMemberDetailId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "memberDetailId is required");
        }

        Optional<MemberDetail> memberDetailOpt = memberDetailsService.findById(request.getMemberDetailId());

        if (!memberDetailOpt.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Member not found: " + request.getMemberDetailId());
        }

        DeathDetail deathDetail;

        if (request.getId() != null) {
            deathDetail = deathDetailRepository.findById(request.getId())
                    .orElse(new DeathDetail());
        } else {
            deathDetail = new DeathDetail();
        }

        // ✅ Set values manually (instead of builder)
        deathDetail.setPlaceOfDeath(request.getPlaceOfDeath());
        deathDetail.setCauseOfDeath(request.getCauseOfDeath());
        deathDetail.setDeathCertificateNumber(request.getDeathCertificateNumber());
        deathDetail.setDateOfDeath(request.getDateOfDeath());
        deathDetail.setNotes(request.getNotes());
        deathDetail.setMemberDetail(memberDetailOpt.get());

        return deathDetailRepository.save(deathDetail);
    }

    public DeathDetailListResponse filteredDeathDetails(DeathDetailRequest request) {
        int pageNo = request.getPageNo() == null ? 0 : request.getPageNo();
        int pageSize = request.getPageSize() == null || request.getPageSize() <= 0 ? 20 : request.getPageSize();
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("id").descending());
        Specification<DeathDetail> spec = DeathDetailSpecification.filterDeaths(request);
        Page<DeathDetail> deathDetails = deathDetailRepository.findAll(spec, pageable);

        Long count = deathDetails.getTotalElements();
        return DeathDetailFactory.buildDeathDetailsListResponse(deathDetails, count);
    }

}
