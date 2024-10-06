package com.masjid.crm.dto.response;

import com.masjid.crm.model.Gender;
import com.masjid.crm.model.MartialStatus;
import lombok.Data;

@Data
public class MemberDetailResponse {

    private Long id;

    private String name;

    private MartialStatus martialStatus;

    private Gender gender;

    private Long age;

    private String educationQualification;

    private String occupation;

    private String phoneNumber;

    private String alternativeNumber;

    private String bloodGroup;

    private Long familyId;

    private String houseHoldName;
}
