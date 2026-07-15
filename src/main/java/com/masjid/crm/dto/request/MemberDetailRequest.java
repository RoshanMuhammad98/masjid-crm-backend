package com.masjid.crm.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.masjid.crm.model.Gender;
import com.masjid.crm.model.MartialStatus;
import lombok.Data;

import java.time.LocalDate;

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

    private String medicalCondition;

    private Boolean hasMedicalIssue;

    private Boolean hasDisability;

    private String disabilityNotes;

    private Boolean isStudent;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfDivorce;

    private String divorcedFromName;

    private String divorceNotes;

    private Boolean isHead;

    private Integer pageNo;

    private Integer pageSize;

    private Long familyDetailId;
}
