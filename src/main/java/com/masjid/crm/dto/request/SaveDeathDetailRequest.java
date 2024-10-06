package com.masjid.crm.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SaveDeathDetailRequest {

    private Long id;

    private String causeOfDeath;

    private String placeOfDeath;

    private LocalDate dateOfDeath;

    private String deathCertificateNumber;

    private Long memberDetailId;
}