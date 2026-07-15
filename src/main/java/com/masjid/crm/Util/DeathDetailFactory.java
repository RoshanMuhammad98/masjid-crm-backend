package com.masjid.crm.Util;

import com.masjid.crm.dto.request.SaveDeathDetailRequest;
import com.masjid.crm.dto.response.DeathDetailListResponse;
import com.masjid.crm.dto.response.DeathDetailResponse;
import com.masjid.crm.entity.DeathDetail;
import com.masjid.crm.entity.MemberDetail;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class DeathDetailFactory {

    public static DeathDetail buildDeathDetail(SaveDeathDetailRequest request, MemberDetail memberDetail) {

        return DeathDetail.builder()
                .placeOfDeath(request.getPlaceOfDeath())
                .causeOfDeath(request.getCauseOfDeath())
                .deathCertificateNumber(request.getDeathCertificateNumber())
                .dateOfDeath(request.getDateOfDeath())
                .notes(request.getNotes())
                .memberDetail(memberDetail)
                .build();
    }

    public static DeathDetailListResponse buildDeathDetailsListResponse(Page<DeathDetail> deathDetails, Long count) {
        List<DeathDetailResponse> responses = deathDetails.stream()
                .map(DeathDetailFactory::toResponse)
                .collect(Collectors.toList());
        return  DeathDetailListResponse.builder().deathDetails(responses).count(count)
                .build();
    }

    public static DeathDetailResponse toResponse(DeathDetail deathDetail) {

        MemberDetail memberDetail = deathDetail.getMemberDetail();
        Long memberId = memberDetail != null ? memberDetail.getId() : null;
        String memberName = memberDetail != null ? memberDetail.getName() : null;
        Long familyDetailId = memberDetail != null && memberDetail.getFamilyDetail() != null
                ? memberDetail.getFamilyDetail().getId()
                : null;

        return DeathDetailResponse.builder()
                .id(deathDetail.getId())
                .placeOfDeath(deathDetail.getPlaceOfDeath())
                .causeOfDeath(deathDetail.getCauseOfDeath())
                .deathCertificateNumber(deathDetail.getDeathCertificateNumber())
                .dateOfDeath(deathDetail.getDateOfDeath())
                .memberId(memberId)
                .memberName(memberName)
                .familyDetailId(familyDetailId)
                .notes(deathDetail.getNotes())
                .build();
    }

}
