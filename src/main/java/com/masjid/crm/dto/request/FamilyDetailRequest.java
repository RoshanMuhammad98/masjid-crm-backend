package com.masjid.crm.dto.request;

import com.masjid.crm.model.FamilyStatus;
import lombok.Data;


@Data
public class FamilyDetailRequest {

    private Long id;

    private String householdName;

    private String phoneNumber;

    private Long totalMembersCount;

    private Double householdIncome;

    private String houseNumber;

    private FamilyStatus familyStatus;

    private String address;

    private Integer pageNo;

    private Integer pageSize;
}
