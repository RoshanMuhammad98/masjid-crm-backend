package com.masjid.crm.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.masjid.crm.model.MarriageMemberType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MarriageDetailRequest {

    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfMarriage;

    private String certificateNumber;

    private String details;

    private Long memberDetailId;

    private Integer pageNo;

    private Integer pageSize;

    private MarriageMemberType marriageMemberType;

    private String marriageMemberName;

    private String marriageMemberPhone;

    private String notes;

    private Long familyDetailId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fromDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate toDate;

    private String placeOfNikkah;

    private String groomName;

    private String groomPhone;

    private String groomAddress;

    private String groomJob;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate groomDateOfBirth;

    private String groomBirthPlace;

    private String brideName;

    private String bridePhone;

    private String brideAddress;

    private String brideJob;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate brideDateOfBirth;

    private String brideBirthPlace;
}
