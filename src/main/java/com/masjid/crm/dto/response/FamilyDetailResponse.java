package com.masjid.crm.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FamilyDetailResponse {

    private Long familyId;

    private String householdName;

    private String phoneNumber;

    private String address;

    private Long totalMembersCount;

    private Double householdIncome;
}
