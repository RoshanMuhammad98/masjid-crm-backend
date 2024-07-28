package com.masjid.crm.Util;

import com.masjid.crm.dto.request.DeathDetailRequest;
import com.masjid.crm.dto.response.DeathDetailListResponse;
import com.masjid.crm.dto.response.DeathDetailResponse;
import com.masjid.crm.dto.response.MarriageDetailListResponse;
import com.masjid.crm.entity.DeathDetail;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class DeathDetailFactory {

    public static DeathDetail buildDeathDetail(DeathDetailRequest request) {

        return DeathDetail.builder()
                .placeOfDeath(request.getPlaceOfDeath())
                .causeOfDeath(request.getCauseOfDeath())
                .deathCertificateNumber(request.getDeathCertificateNumber())
                .dateOfDeath(request.getDateOfDeath())
                .build();
    }

    public static DeathDetailListResponse buildDeathDetailsListResponse(Page<DeathDetail> deathDetails, Long count) {
        List<DeathDetailResponse> responses = deathDetails.stream()
                .map(DeathDetailFactory::toResponse)
                .collect(Collectors.toList());
        return  DeathDetailListResponse.builder().deathDetails(responses).count(count)
                .build();
    }

    private static DeathDetailResponse toResponse(DeathDetail deathDetail) {
        return DeathDetailResponse.builder()
                .id(deathDetail.getId())
                .placeOfDeath(deathDetail.getPlaceOfDeath())
                .causeOfDeath(deathDetail.getCauseOfDeath())
                .deathCertificateNumber(deathDetail.getDeathCertificateNumber())
                .dateOfDeath(deathDetail.getDateOfDeath())
                .build();
    }
}
