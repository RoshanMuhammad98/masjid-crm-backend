package com.masjid.crm.controller;

import com.masjid.crm.dto.request.FinancialAccountRequest;
import com.masjid.crm.dto.response.FinancialAccountListResponse;
import com.masjid.crm.dto.response.FinancialSummaryResponse;
import com.masjid.crm.entity.FinancialAccount;
import com.masjid.crm.model.PaymentStatus;
import com.masjid.crm.service.FinancialAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

/**
 * Handles routing for masjid financial account transactions
 * (income, expenses, categorized records, and summaries).
 */
@RestController
@RequestMapping("/financial-account")
@CrossOrigin(origins = "*", maxAge = 3600)
public class FinancialAccountController {

    @Autowired
    private FinancialAccountService financialAccountService;

    @PostMapping("/save")
    public FinancialAccount save(@RequestBody @Valid FinancialAccountRequest request) {
        return financialAccountService.save(request);
    }

    @PostMapping("/filtered")
    public FinancialAccountListResponse filtered(@RequestBody @Valid FinancialAccountRequest request) {
        return financialAccountService.filter(request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        financialAccountService.delete(id);
    }

    @PatchMapping("/{id}/payment-status")
    public FinancialAccount updatePaymentStatus(@PathVariable Long id,
                                                @RequestParam PaymentStatus status) {
        return financialAccountService.updatePaymentStatus(id, status);
    }

    @GetMapping("/summary")
    public FinancialSummaryResponse summary(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {
        return financialAccountService.summary(fromDate, toDate);
    }
}
