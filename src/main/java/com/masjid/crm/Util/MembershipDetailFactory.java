package com.masjid.crm.Util;

import com.masjid.crm.dto.request.MembershipDetailRequest;
import com.masjid.crm.dto.response.MembershipDetailListResponse;
import com.masjid.crm.dto.response.MembershipDetailResponse;
import com.masjid.crm.entity.FamilyDetail;
import com.masjid.crm.entity.MembershipDetail;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class MembershipDetailFactory {

    public static MembershipDetail buildMembershipDetail(MembershipDetailRequest request, FamilyDetail familyDetail) {

        return MembershipDetail.builder()
                .memberShipType(request.getMemberShipType())
                .amount(request.getAmount())
                .paymentStatus(request.getPaymentStatus())
                .notes(request.getNotes())
                .familyDetail(familyDetail)
                .build();
    }

    public static MembershipDetailListResponse buildMembershipDetailsListResponse(Page<MembershipDetail> membershipDetails, Long count) {
        List<MembershipDetailResponse> responses = membershipDetails.stream()
                .map(MembershipDetailFactory::getMemberShipDetail)
                .collect(Collectors.toList());
        return  MembershipDetailListResponse.builder().membershipDetails(responses).count(count)
                .build();
    }

    private static MembershipDetailResponse getMemberShipDetail(MembershipDetail membershipDetail) {
        return MembershipDetailResponse.builder()
                .id(membershipDetail.getId())
                .familyDetailId(membershipDetail.getFamilyDetail().getId())
                .memberShipType(membershipDetail.getMemberShipType())
                .amount(membershipDetail.getAmount())
                .paymentStatus(membershipDetail.getPaymentStatus())
                .notes(membershipDetail.getNotes())
                .build();
    }

}
