package com.masjid.crm.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class DeathDetailResponse {

    private Long id;

    private String placeOfDeath;

    private String causeOfDeath;

    private String deathCertificateNumber;

    private LocalDate dateOfDeath;

    private Long memberId;

    private String memberName;

    private Long familyDetailId;

    private String notes;
}
