package com.masjid.crm.specification;

import com.masjid.crm.dto.request.MembershipDetailRequest;
import com.masjid.crm.entity.MembershipDetail;
import com.masjid.crm.model.MemberShipType;
import com.masjid.crm.model.PaymentStatus;
import org.springframework.data.jpa.domain.Specification;

public class MembershipDetailSpecification {

    public static Specification<MembershipDetail> filterMemberships(MembershipDetailRequest request) {

        Specification<MembershipDetail> spec1 = byFamilyDetailId(request.getFamilyDetailId());
        Specification<MembershipDetail> spec2 = byMemberShipType(request.getMemberShipType());
        Specification<MembershipDetail> spec3 = byPaymentStatus(request.getPaymentStatus());

        return Specification.where(spec1).and(spec2).and(spec3);
    }

    private static Specification<MembershipDetail> byFamilyDetailId(Long familyDetailId) {
        return (root, query, builder) -> {
            if (familyDetailId != null) {
                return builder.equal(root.get("familyDetail").get("id"), familyDetailId);
            }
            return builder.conjunction();
        };
    }

    private static Specification<MembershipDetail> byMemberShipType(MemberShipType memberShipType) {
        return (root, query, builder) -> {
            if (memberShipType != null) {
                return builder.equal(root.get("memberShipType"), memberShipType);
            }
            return builder.conjunction();
        };
    }

    private static Specification<MembershipDetail> byPaymentStatus(PaymentStatus paymentStatus) {
        return (root, query, builder) -> {
            if (paymentStatus != null) {
                return builder.equal(root.get("paymentStatus"), paymentStatus);
            }
            return builder.conjunction();
        };
    }

}
