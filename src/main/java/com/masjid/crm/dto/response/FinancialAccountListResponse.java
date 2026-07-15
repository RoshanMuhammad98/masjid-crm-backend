package com.masjid.crm.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FinancialAccountListResponse {

    private List<FinancialAccountResponse> transactions;

    private Long count;
}
