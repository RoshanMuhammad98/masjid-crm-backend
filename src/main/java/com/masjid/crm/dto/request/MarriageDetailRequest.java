package com.masjid.crm.dto.request;

import com.masjid.crm.model.MarriageMemberType;
import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDate;

@Data
public class MarriageDetailRequest {

    private Long id;

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
}
