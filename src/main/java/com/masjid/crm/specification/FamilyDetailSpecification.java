package com.masjid.crm.specification;

import com.masjid.crm.dto.request.FamilyDetailRequest;
import com.masjid.crm.entity.FamilyDetail;
import org.springframework.data.jpa.domain.Specification;

public class FamilyDetailSpecification {

    public static Specification<FamilyDetail> filterfamilies(FamilyDetailRequest request) {

        Specification<FamilyDetail> enquirySpec1 = byHouseHoldName(request.getHouseholdName());
        Specification<FamilyDetail> enquirySpec2 = byPhoneNumber(request.getPhoneNumber());

        return Specification.where(enquirySpec1).and(enquirySpec2);
    }

    private static Specification<FamilyDetail> byPhoneNumber(String phone) {
        return (root, query, builder) -> {
            if (phone != null && !phone.isEmpty()) {
                return builder.or(
                        builder.like(builder.lower(root.get("phoneNumber")), "%" + phone.toLowerCase() + "%"));
            }
            return builder.conjunction();
        };
    }

    private static Specification<FamilyDetail> byHouseHoldName(String householdName) {
        return (root, query, builder) -> {
            if (householdName != null && !householdName.isEmpty()) {
                return builder.or(
                        builder.like(builder.lower(root.get("householdName")), "%" + householdName.toLowerCase() + "%"));
            }
            return builder.conjunction();
        };
    }

}
