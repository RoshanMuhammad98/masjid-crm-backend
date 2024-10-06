package com.masjid.crm.dto.request;

import lombok.Data;

@Data
public class SavedFamilyDetailRequest {

        private Long id;

        private String householdName;

        private String phoneNumber;

        private String address;

        private Long totalMembersCount;

        private Double householdIncome;

}
