package com.masjid.crm.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.masjid.crm.model.FinancialCategory;
import com.masjid.crm.model.FinancialSource;
import com.masjid.crm.model.PaymentMethod;
import com.masjid.crm.model.PaymentStatus;
import com.masjid.crm.model.TransactionType;
import lombok.Data;

import java.util.Date;

@Data
public class FinancialAccountRequest {

    private Long id;

    private TransactionType transactionType;

    private FinancialCategory category;

    private Double amount;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date transactionDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date fromDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date toDate;

    private PaymentMethod paymentMethod;

    private String referenceNumber;

    private String payerOrPayeeName;

    private String description;

    private PaymentStatus paymentStatus;

    private FinancialSource sourceType;

    private Integer pageNo;

    private Integer pageSize;
}
