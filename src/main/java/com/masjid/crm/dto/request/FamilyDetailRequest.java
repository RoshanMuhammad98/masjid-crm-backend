package com.masjid.crm.dto.request;

import lombok.Data;


@Data
public class FamilyDetailRequest {

    private Long id;

    private String householdName;

    private String phoneNumber;

    private String address;

    private Long totalMembersCount;

    private Double householdIncome;

    private Integer pageNo;

    private Integer pageSize;
}
