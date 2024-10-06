package com.masjid.crm.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DeathDetailRequest {

    private Long id;

    private String causeOfDeath;

    private String placeOfDeath;

    private LocalDate dateOfDeath;

    private String deathCertificateNumber;

    private Integer pageNo;

    private Integer pageSize;

    private Long memberDetailId;

    private String memberName;

    private String dateFilter;

    private LocalDate fromDate;

    private LocalDate toDate;

    private String notes;

}

