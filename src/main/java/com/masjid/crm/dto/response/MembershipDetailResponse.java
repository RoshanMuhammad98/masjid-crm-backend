package com.masjid.crm.dto.response;

import com.masjid.crm.model.MemberShipType;
import com.masjid.crm.model.PaymentStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MembershipDetailResponse {

    private Long id;

    private Long familyDetailId;

    private MemberShipType memberShipType;

    private Double amount;

    private PaymentStatus paymentStatus;

    private String notes;
}
