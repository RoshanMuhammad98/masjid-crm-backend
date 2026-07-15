package com.masjid.crm.dto.response;

import com.masjid.crm.model.FamilyStatus;
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

    private String houseNumber;

    private FamilyStatus familyStatus;
}
