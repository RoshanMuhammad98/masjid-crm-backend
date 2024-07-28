package com.masjid.crm.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MembershipDetailListResponse {

    private List<MembershipDetailResponse> membershipDetails;

    private Long count;
}
