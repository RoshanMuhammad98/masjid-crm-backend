package com.masjid.crm.entity;

import com.masjid.crm.model.MemberShipType;
import com.masjid.crm.model.MembershipMemberType;
import com.masjid.crm.model.PaymentMethod;
import com.masjid.crm.model.PaymentStatus;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "membership_detail")
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MembershipDetail  extends Audit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "family_detail_id")
    private FamilyDetail familyDetail;

    @Column
    @Enumerated(EnumType.STRING)
    private MemberShipType memberShipType;

    @Column
    private Double amount;

    @Column
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column
    private String notes;

    @Column
    @Enumerated(EnumType.STRING)
    private MembershipMemberType membershipMemberType;

    @Column
    private String otherPersonName;

    @Column
    private String otherPersonPhoneNumber;

    @Column
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

}
