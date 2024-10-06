package com.masjid.crm.dto.request;

import com.masjid.crm.model.MemberShipType;
import com.masjid.crm.model.MembershipMemberType;
import com.masjid.crm.model.PaymentStatus;
import lombok.Data;

import javax.persistence.Column;

@Data
public class MembershipDetailRequest {

    private Long id;

    private Long familyDetailId;

    private MemberShipType memberShipType;

    private Double amount;

    private PaymentStatus paymentStatus;

    private String notes;

    private Integer pageNo;

    private Integer pageSize;

    private MembershipMemberType membershipMemberType;

    private String otherPersonName;

    private String otherPersonPhoneNumber;

}
