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
        if (marriageDetail == null) {
            marriageDetail = new MarriageDetail();
        }
        marriageDetail.setDateOfMarriage(request.getDateOfMarriage());
        marriageDetail.setCertificateNumber(request.getCertificateNumber());
        marriageDetail.setDetails(request.getDetails());
        marriageDetail.setMarriageMemberName(request.getMarriageMemberName());
        marriageDetail.setMarriageMemberPhone(request.getMarriageMemberPhone());
        marriageDetail.setMarriageMemberType(request.getMarriageMemberType());
        marriageDetail.setMemberDetail(memberDetail);
        marriageDetail.setNotes(request.getNotes());
        marriageDetail.setPlaceOfNikkah(request.getPlaceOfNikkah());
        marriageDetail.setGroomName(request.getGroomName());
        marriageDetail.setGroomPhone(request.getGroomPhone());
        marriageDetail.setGroomAddress(request.getGroomAddress());
        marriageDetail.setGroomJob(request.getGroomJob());
        marriageDetail.setGroomDateOfBirth(request.getGroomDateOfBirth());
        marriageDetail.setGroomBirthPlace(request.getGroomBirthPlace());
        marriageDetail.setBrideName(request.getBrideName());
        marriageDetail.setBridePhone(request.getBridePhone());
        marriageDetail.setBrideAddress(request.getBrideAddress());
        marriageDetail.setBrideJob(request.getBrideJob());
        marriageDetail.setBrideDateOfBirth(request.getBrideDateOfBirth());
        marriageDetail.setBrideBirthPlace(request.getBrideBirthPlace());
        return marriageDetail;
    }

    public static MarriageDetailListResponse buildMarriageDetailsListResponse(Page<MarriageDetail> marriageDetails, Long count) {
        List<MarriageDetailResponse> responses = marriageDetails.stream()
                .map(MarriageDetailFactory::toResponse)
                .collect(Collectors.toList());
        return MarriageDetailListResponse.builder().marriageDetails(responses).count(count)
                .build();
    }

    public static MarriageDetailResponse toResponse(MarriageDetail marriageDetail) {

        MemberDetail memberDetail = marriageDetail.getMemberDetail();
        return MarriageDetailResponse.builder()
                .id(marriageDetail.getId())
                .dateOfMarriage(marriageDetail.getDateOfMarriage())
                .certificateNumber(marriageDetail.getCertificateNumber())
                .details(marriageDetail.getDetails())
                .memberDetailId(memberDetail != null ? memberDetail.getId() : null)
                .memberName(memberDetail != null ? memberDetail.getName() : null)
                .marriageMemberName(marriageDetail.getMarriageMemberName())
                .marriageMemberPhone(marriageDetail.getMarriageMemberPhone())
                .marriageMemberType(marriageDetail.getMarriageMemberType())
                .familyDetailId(memberDetail != null && memberDetail.getFamilyDetail() != null
                        ? memberDetail.getFamilyDetail().getId() : null)
                .notes(marriageDetail.getNotes())
                .placeOfNikkah(marriageDetail.getPlaceOfNikkah())
                .groomName(marriageDetail.getGroomName())
                .groomPhone(marriageDetail.getGroomPhone())
                .groomAddress(marriageDetail.getGroomAddress())
                .groomJob(marriageDetail.getGroomJob())
                .groomDateOfBirth(marriageDetail.getGroomDateOfBirth())
                .groomBirthPlace(marriageDetail.getGroomBirthPlace())
                .brideName(marriageDetail.getBrideName())
                .bridePhone(marriageDetail.getBridePhone())
                .brideAddress(marriageDetail.getBrideAddress())
                .brideJob(marriageDetail.getBrideJob())
                .brideDateOfBirth(marriageDetail.getBrideDateOfBirth())
                .brideBirthPlace(marriageDetail.getBrideBirthPlace())
                .build();
    }

}
