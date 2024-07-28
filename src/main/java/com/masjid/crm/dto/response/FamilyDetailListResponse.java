package com.masjid.crm.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FamilyDetailListResponse {

    private List<FamilyDetailResponse> familyDetailResponses;

    private Long count;
}
