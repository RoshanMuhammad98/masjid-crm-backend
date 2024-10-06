package com.masjid.crm.Util;

import com.masjid.crm.dto.request.MarriageDetailRequest;
import com.masjid.crm.dto.response.MarriageDetailListResponse;
import com.masjid.crm.dto.response.MarriageDetailResponse;
import com.masjid.crm.entity.MarriageDetail;
import com.masjid.crm.entity.MemberDetail;
import com.masjid.crm.model.MarriageMemberType;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class MarriageDetailFactory {

    public static MarriageDetail buildMarriageDetail(MarriageDetailRequest request, MemberDetail memberDetail, MarriageDetail marriageDetail) {

        marriageDetail = MarriageDetail.builder()
                .dateOfMarriage(request.getDateOfMarriage())
                .certificateNumber(request.getCertificateNumber())
                .details(request.getDetails())
                .marriageMemberName(request.getMarriageMemberName())
                .marriageMemberPhone(request.getMarriageMemberPhone())
                .marriageMemberType(request.getMarriageMemberType())
                .memberDetail(memberDetail)
                .notes(request.getNotes())
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

        MemberDetail memberDetail = marriageDetail.getMemberDetail();
        return MarriageDetailResponse.builder()
                .id(marriageDetail.getId())
                .dateOfMarriage(marriageDetail.getDateOfMarriage())
                .certificateNumber(marriageDetail.getCertificateNumber())
                .details(marriageDetail.getDetails())
                .memberDetailId(memberDetail.getId())
                .memberName(memberDetail.getName())
                .marriageMemberName(marriageDetail.getMarriageMemberName())
                .marriageMemberPhone(marriageDetail.getMarriageMemberPhone())
                .marriageMemberType(marriageDetail.getMarriageMemberType())
                .familyDetailId(memberDetail.getFamilyDetail().getId())
                .notes(marriageDetail.getNotes())
                .build();
    }

}
