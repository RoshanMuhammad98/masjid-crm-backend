package com.masjid.crm.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MemberDetailListResponse {

    private List<MemberDetailResponse> memberDetails;

    private Long count;
}
