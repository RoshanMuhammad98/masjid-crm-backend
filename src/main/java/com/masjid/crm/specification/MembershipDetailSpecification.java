package com.masjid.crm.specification;

import com.masjid.crm.dto.request.MembershipDetailRequest;
import com.masjid.crm.entity.MembershipDetail;
import com.masjid.crm.model.MemberShipType;
import com.masjid.crm.model.PaymentMethod;
import com.masjid.crm.model.PaymentStatus;
import org.springframework.data.jpa.domain.Specification;

public class MembershipDetailSpecification {

    public static Specification<MembershipDetail> filterMemberships(MembershipDetailRequest request) {
        return Specification
                .where(byFamilyDetailId(request.getFamilyDetailId()))
                .and(byMemberShipType(request.getMemberShipType()))
                .and(byPaymentStatus(request.getPaymentStatus()))
                .and(byPaymentMethod(request.getPaymentMethod()));
    }

    private static Specification<MembershipDetail> byFamilyDetailId(Long familyDetailId) {
        return (root, query, builder) -> familyDetailId == null
                ? builder.conjunction()
                : builder.equal(root.get("familyDetail").get("id"), familyDetailId);
    }

    private static Specification<MembershipDetail> byMemberShipType(MemberShipType memberShipType) {
        return (root, query, builder) -> memberShipType == null
                ? builder.conjunction()
                : builder.equal(root.get("memberShipType"), memberShipType);
    }

    private static Specification<MembershipDetail> byPaymentStatus(PaymentStatus paymentStatus) {
        return (root, query, builder) -> paymentStatus == null
                ? builder.conjunction()
                : builder.equal(root.get("paymentStatus"), paymentStatus);
    }

    private static Specification<MembershipDetail> byPaymentMethod(PaymentMethod paymentMethod) {
        return (root, query, builder) -> paymentMethod == null
                ? builder.conjunction()
                : builder.equal(root.get("paymentMethod"), paymentMethod);
    }
}
