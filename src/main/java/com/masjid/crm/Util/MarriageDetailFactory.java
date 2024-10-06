package com.masjid.crm.Util;

import com.masjid.crm.dto.request.MarriageDetailRequest;
import com.masjid.crm.dto.response.MarriageDetailListResponse;
import com.masjid.crm.dto.response.MarriageDetailResponse;
import com.masjid.crm.entity.MarriageDetail;
import com.masjid.crm.entity.MemberDetail;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class MarriageDetailFactory {

    public static MarriageDetail buildMarriageDetail(MarriageDetailRequest request, MemberDetail memberDetail, MarriageDetail marriageDetail) {

        marriageDetail = MarriageDetail.builder()
                .dateOfMarriage(request.getDateOfMarriage())
                .certificateNumber(request.getCertificateNumber())
                .details(request.getDetails())
                .memberDetail(memberDetail)
                .build();
        return marriageDetail;
    }

    public static MarriageDetailListResponse buildMarriageDetailsListResponse(Page<MarriageDetail> marriageDetails, Long count) {
        List<MarriageDetailResponse> responses = marriageDetails.stream()
                .map(MarriageDetailFactory::toResponse)
                .collect(Collectors.toList());
        return MarriageDetailListResponse.builder().marriageDetails(responses).count(count)
                .build();
    }

    private static MarriageDetailResponse toResponse(MarriageDetail marriageDetail) {
        return MarriageDetailResponse.builder()
                .id(marriageDetail.getId())
                .dateOfMarriage(marriageDetail.getDateOfMarriage())
                .certificateNumber(marriageDetail.getCertificateNumber())
                .details(marriageDetail.getDetails())
                .memberDetailId(marriageDetail.getMemberDetail().getId())
                .memberName(marriageDetail.getMemberDetail().getName())
                .build();
    }

}
