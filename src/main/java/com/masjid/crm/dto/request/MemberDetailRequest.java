package com.masjid.crm.dto.request;

import com.masjid.crm.model.Gender;
import com.masjid.crm.model.MartialStatus;
import lombok.Data;

@Data
public class MemberDetailRequest {

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

    private Integer pageNo;

    private Integer pageSize;
}
