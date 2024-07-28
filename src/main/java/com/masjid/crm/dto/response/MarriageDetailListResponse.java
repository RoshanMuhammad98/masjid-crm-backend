package com.masjid.crm.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MarriageDetailListResponse {

    private List<MarriageDetailResponse> marriageDetails;

    private Long count;
}
