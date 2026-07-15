package com.masjid.crm.specification;

import com.masjid.crm.dto.request.FinancialAccountRequest;
import com.masjid.crm.entity.FinancialAccount;
import com.masjid.crm.model.FinancialCategory;
import com.masjid.crm.model.FinancialSource;
import com.masjid.crm.model.PaymentMethod;
import com.masjid.crm.model.PaymentStatus;
import com.masjid.crm.model.TransactionType;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class FinancialAccountSpecification {

    public static Specification<FinancialAccount> filter(FinancialAccountRequest request) {
        return Specification
                .where(byTransactionType(request.getTransactionType()))
                .and(byCategory(request.getCategory()))
                .and(byPaymentMethod(request.getPaymentMethod()))
                .and(byPaymentStatus(request.getPaymentStatus()))
                .and(bySourceType(request.getSourceType()))
                .and(byFromDate(request.getFromDate()))
                .and(byToDate(request.getToDate()));
    }

    private static Specification<FinancialAccount> byPaymentStatus(PaymentStatus status) {
        return (root, query, builder) -> status == null
                ? builder.conjunction()
                : builder.equal(root.get("paymentStatus"), status);
    }

    private static Specification<FinancialAccount> bySourceType(FinancialSource source) {
        return (root, query, builder) -> source == null
                ? builder.conjunction()
                : builder.equal(root.get("sourceType"), source);
    }

    private static Specification<FinancialAccount> byTransactionType(TransactionType type) {
        return (root, query, builder) -> type == null
                ? builder.conjunction()
                : builder.equal(root.get("transactionType"), type);
    }

    private static Specification<FinancialAccount> byCategory(FinancialCategory category) {
        return (root, query, builder) -> category == null
                ? builder.conjunction()
                : builder.equal(root.get("category"), category);
    }

    private static Specification<FinancialAccount> byPaymentMethod(PaymentMethod method) {
        return (root, query, builder) -> method == null
                ? builder.conjunction()
                : builder.equal(root.get("paymentMethod"), method);
    }

    private static Specification<FinancialAccount> byFromDate(Date fromDate) {
        return (root, query, builder) -> fromDate == null
                ? builder.conjunction()
                : builder.greaterThanOrEqualTo(root.get("transactionDate"), fromDate);
    }

    private static Specification<FinancialAccount> byToDate(Date toDate) {
        return (root, query, builder) -> toDate == null
                ? builder.conjunction()
                : builder.lessThanOrEqualTo(root.get("transactionDate"), toDate);
    }
}
