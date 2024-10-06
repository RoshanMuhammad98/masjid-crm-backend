package com.masjid.crm.Util;

import com.masjid.crm.dto.request.SaveDeathDetailRequest;
import com.masjid.crm.dto.response.DeathDetailListResponse;
import com.masjid.crm.dto.response.DeathDetailResponse;
import com.masjid.crm.entity.DeathDetail;
import com.masjid.crm.entity.MemberDetail;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DeathDetailFactory {

    public static DeathDetail buildDeathDetail(SaveDeathDetailRequest request, DeathDetail deathDetail) {

        deathDetail = DeathDetail.builder()
                .placeOfDeath(request.getPlaceOfDeath())
                .causeOfDeath(request.getCauseOfDeath())
                .deathCertificateNumber(request.getDeathCertificateNumber())
                .dateOfDeath(request.getDateOfDeath())
                .notes(request.getNotes())
                .build();
        return deathDetail;
    }

    public static DeathDetailListResponse buildDeathDetailsListResponse(Page<DeathDetail> deathDetails, Long count) {
        List<DeathDetailResponse> responses = deathDetails.stream()
                .map(DeathDetailFactory::toResponse)
                .collect(Collectors.toList());
        return  DeathDetailListResponse.builder().deathDetails(responses).count(count)
                .build();
    }

    private static DeathDetailResponse toResponse(DeathDetail deathDetail) {

        MemberDetail memberDetail = deathDetail.getMemberDetail();
        return DeathDetailResponse.builder()
                .id(deathDetail.getId())
                .placeOfDeath(deathDetail.getPlaceOfDeath())
                .causeOfDeath(deathDetail.getCauseOfDeath())
                .deathCertificateNumber(deathDetail.getDeathCertificateNumber())
                .dateOfDeath(deathDetail.getDateOfDeath())
                .memberId(memberDetail.getId())
                .memberName(memberDetail.getName())
                .familyDetailId(memberDetail.getFamilyDetail().getId())
                .notes(deathDetail.getNotes())
                .build();
    }

}
