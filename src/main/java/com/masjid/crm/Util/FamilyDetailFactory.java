package com.masjid.crm.Util;

import com.masjid.crm.dto.request.FamilyDetailRequest;
import com.masjid.crm.dto.response.FamilyDetailListResponse;
import com.masjid.crm.dto.response.FamilyDetailResponse;
import com.masjid.crm.entity.FamilyDetail;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class FamilyDetailFactory {

    public static FamilyDetail buildFamilyDetail(FamilyDetailRequest request) {
        return FamilyDetail.builder()
                .householdName(request.getHouseholdName())
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .totalMembersCount(request.getTotalMembersCount())
                .householdIncome(request.getHouseholdIncome())
                .build();
    }

    public static FamilyDetailListResponse buildFamilyDetailsListResponse(Page<FamilyDetail> familyDetails, Long count) {
        List<FamilyDetailResponse> familyDetailResponses = familyDetails.stream()
                .map(FamilyDetailFactory::getFamilyDetailListResponse).collect(Collectors.toList());
        return FamilyDetailListResponse.builder().familyDetailResponses(familyDetailResponses).count(count)
                .build();
    }

    private static FamilyDetailResponse getFamilyDetailListResponse(FamilyDetail familyDetail) {

        return FamilyDetailResponse.builder()
                .householdName(familyDetail.getHouseholdName())
                .address(familyDetail.getAddress())
                .phoneNumber(familyDetail.getPhoneNumber())
                .totalMembersCount(familyDetail.getTotalMembersCount())
                .householdIncome(familyDetail.getHouseholdIncome())
                .build();
    }

}
