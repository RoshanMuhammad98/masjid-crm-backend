package com.masjid.crm.dto.response;

import com.masjid.crm.model.FinancialCategory;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class FinancialSummaryResponse {

    private Double totalIncome;

    private Double totalExpense;

    private Double balance;

    private Double pendingIncome;

    private Double pendingExpense;

    private Map<FinancialCategory, Double> incomeByCategory;

    private Map<FinancialCategory, Double> expenseByCategory;
}
