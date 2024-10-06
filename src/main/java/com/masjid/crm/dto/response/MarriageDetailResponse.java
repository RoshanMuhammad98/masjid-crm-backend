package com.masjid.crm.dto.response;

import com.masjid.crm.model.MarriageMemberType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class MarriageDetailResponse {

    private Long id;

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

}
