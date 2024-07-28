package com.masjid.crm.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DeathDetailListResponse {

    private List<DeathDetailResponse> deathDetails;

    private Long count;
}
