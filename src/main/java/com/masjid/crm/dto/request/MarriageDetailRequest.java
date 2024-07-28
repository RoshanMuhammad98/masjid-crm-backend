package com.masjid.crm.dto.request;

import lombok.Data;

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
}
