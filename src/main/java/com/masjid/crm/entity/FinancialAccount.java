package com.masjid.crm.entity;

import com.masjid.crm.model.FinancialCategory;
import com.masjid.crm.model.FinancialSource;
import com.masjid.crm.model.PaymentMethod;
import com.masjid.crm.model.PaymentStatus;
import com.masjid.crm.model.TransactionType;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "financial_account")
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FinancialAccount extends Audit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Column
    @Enumerated(EnumType.STRING)
    private FinancialCategory category;

    @Column
    private Double amount;

    @Column
    @Temporal(TemporalType.DATE)
    private Date transactionDate;

    @Column
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Column
    private String referenceNumber;

    @Column
    private String payerOrPayeeName;

    @Column(length = 1000)
    private String description;

    @Column
    @Enumerated(EnumType.STRING)
    private FinancialSource sourceType;

    @Column
    private Long sourceId;

    @Column
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

}
