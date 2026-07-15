package com.masjid.crm.Util;

import com.masjid.crm.dto.request.FamilyDetailRequest;
import com.masjid.crm.dto.request.SavedFamilyDetailRequest;
import com.masjid.crm.dto.response.FamilyDetailListResponse;
import com.masjid.crm.dto.response.FamilyDetailResponse;
import com.masjid.crm.entity.FamilyDetail;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class FamilyDetailFactory {

    public static FamilyDetail buildFamilyDetail(SavedFamilyDetailRequest request, FamilyDetail familyDetail) {
        if (familyDetail == null) {
            familyDetail = new FamilyDetail();
        }
        familyDetail.setHouseholdName(request.getHouseholdName());
        familyDetail.setPhoneNumber(request.getPhoneNumber());
        familyDetail.setAddress(request.getAddress());
        familyDetail.setTotalMembersCount(request.getTotalMembersCount());
        familyDetail.setHouseholdIncome(request.getHouseholdIncome());
        familyDetail.setHouseNumber(request.getHouseNumber());
        familyDetail.setFamilyStatus(request.getFamilyStatus());
        return familyDetail;
    }

    public static FamilyDetailListResponse buildFamilyDetailsListResponse(Page<FamilyDetail> familyDetails, Long count) {
        List<FamilyDetailResponse> familyDetailResponses = familyDetails.stream()
                .map(FamilyDetailFactory::getFamilyDetailListResponse).collect(Collectors.toList());
        return FamilyDetailListResponse.builder().familyDetailResponses(familyDetailResponses).count(count)
                .build();
    }

    static FamilyDetailResponse getFamilyDetailListResponse(FamilyDetail familyDetail) {

        return FamilyDetailResponse.builder()
                .familyId(familyDetail.getId())
                .householdName(familyDetail.getHouseholdName())
                .address(familyDetail.getAddress())
                .phoneNumber(familyDetail.getPhoneNumber())
                .totalMembersCount(familyDetail.getTotalMembersCount())
                .householdIncome(familyDetail.getHouseholdIncome())
                .houseNumber(familyDetail.getHouseNumber())
                .familyStatus(familyDetail.getFamilyStatus())
                .build();
    }

    public static FamilyDetailResponse toResponse(FamilyDetail familyDetail) {
        return getFamilyDetailListResponse(familyDetail);
    }

}
