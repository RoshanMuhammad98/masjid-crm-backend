package com.masjid.crm.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.masjid.crm.model.MarriageMemberType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class MarriageDetailResponse {

    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfMarriage;

    private String certificateNumber;

    private String details;

    private Long memberDetailId;

    private String memberName;

    private MarriageMemberType marriageMemberType;

    private String marriageMemberName;

    private String marriageMemberPhone;

    private Long familyDetailId;

    private String notes;

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
