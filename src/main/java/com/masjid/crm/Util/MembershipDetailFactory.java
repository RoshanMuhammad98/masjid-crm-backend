package com.masjid.crm.Util;

import com.masjid.crm.dto.request.MembershipDetailRequest;
import com.masjid.crm.dto.response.MembershipDetailListResponse;
import com.masjid.crm.dto.response.MembershipDetailResponse;
import com.masjid.crm.entity.FamilyDetail;
import com.masjid.crm.entity.MembershipDetail;
import com.masjid.crm.model.MembershipMemberType;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class MembershipDetailFactory {

    public static MembershipDetail buildMembershipDetail(MembershipDetailRequest request, FamilyDetail familyDetail) {

        MembershipDetail membershipDetail = MembershipDetail.builder()
                .memberShipType(request.getMemberShipType())
                .amount(request.getAmount())
                .paymentStatus(request.getPaymentStatus())
                .notes(request.getNotes())
                .familyDetail(familyDetail)
                .membershipMemberType(request.getMembershipMemberType())
                .build();

        if (MembershipMemberType.OTHER.equals(request.getMembershipMemberType())) {
            membershipDetail.setOtherPersonName(request.getOtherPersonName());
            membershipDetail.setOtherPersonPhoneNumber(request.getOtherPersonPhoneNumber());
        }
        return membershipDetail;
    }

    public static MembershipDetailListResponse buildMembershipDetailsListResponse(Page<MembershipDetail> membershipDetails, Long count) {
        List<MembershipDetailResponse> responses = membershipDetails.stream()
                .map(MembershipDetailFactory::getMemberShipDetail)
                .collect(Collectors.toList());
        return  MembershipDetailListResponse.builder().membershipDetails(responses).count(count)
                .build();
    }

    private static MembershipDetailResponse getMemberShipDetail(MembershipDetail membershipDetail) {

        FamilyDetail familyDetail = membershipDetail.getFamilyDetail();
        MembershipDetailResponse membershipDetailResponse =  MembershipDetailResponse.builder()
                .id(membershipDetail.getId())
                .familyDetailId(familyDetail != null ? familyDetail.getId() : null)
                .householdName(familyDetail != null ? familyDetail.getHouseholdName() : null)
                .contactNumber(familyDetail != null ? familyDetail.getPhoneNumber() : null)
                .memberShipType(membershipDetail.getMemberShipType())
                .amount(membershipDetail.getAmount())
                .paymentStatus(membershipDetail.getPaymentStatus())
                .paymentMethod(membershipDetail.getPaymentMethod())
                .membershipMemberType(membershipDetail.getMembershipMemberType())
                .otherPersonName(membershipDetail.getOtherPersonName())
                .notes(membershipDetail.getNotes())
                .build();

        if (membershipDetail.getMembershipMemberType() == MembershipMemberType.OTHER
                && membershipDetail.getOtherPersonPhoneNumber() != null) {
            membershipDetailResponse.setContactNumber(membershipDetail.getOtherPersonPhoneNumber());
        }

//        if (membershipDetail.getMembershipMemberType().equals(MembershipMemberType.OTHER)) {
//            membershipDetailResponse.setOtherPersonName(membershipDetail.getOtherPersonName());
//            membershipDetailResponse.setContactNumber(membershipDetail.getOtherPersonPhoneNumber());
//        }
//        else {
//            membershipDetailResponse.setContactNumber(familyDetail.getPhoneNumber());
//        }
        return membershipDetailResponse;
    }

}
