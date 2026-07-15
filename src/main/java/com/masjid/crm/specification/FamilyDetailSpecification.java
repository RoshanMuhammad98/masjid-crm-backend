package com.masjid.crm.specification;

import com.masjid.crm.dto.request.FamilyDetailRequest;
import com.masjid.crm.entity.FamilyDetail;
import com.masjid.crm.model.FamilyStatus;
import org.springframework.data.jpa.domain.Specification;

public class FamilyDetailSpecification {

    public static Specification<FamilyDetail> filterfamilies(FamilyDetailRequest request) {
        return Specification
                .where(byHouseHoldName(request.getHouseholdName()))
                .and(byPhoneNumber(request.getPhoneNumber()))
                .and(byFamilyStatus(request.getFamilyStatus()))
                .and(byHouseNumber(request.getHouseNumber()))
                .and(byAddress(request.getAddress()));
    }

    private static Specification<FamilyDetail> byHouseHoldName(String householdName) {
        return (root, query, builder) -> {
            if (householdName != null && !householdName.isEmpty()) {
                return builder.like(builder.lower(root.get("householdName")),
                        "%" + householdName.toLowerCase() + "%");
            }
            return builder.conjunction();
        };
    }

    private static Specification<FamilyDetail> byPhoneNumber(String phone) {
        return (root, query, builder) -> {
            if (phone != null && !phone.isEmpty()) {
                return builder.like(builder.lower(root.get("phoneNumber")),
                        "%" + phone.toLowerCase() + "%");
            }
            return builder.conjunction();
        };
    }

    private static Specification<FamilyDetail> byFamilyStatus(FamilyStatus status) {
        return (root, query, builder) -> status == null
                ? builder.conjunction()
                : builder.equal(root.get("familyStatus"), status);
    }

    private static Specification<FamilyDetail> byHouseNumber(String houseNumber) {
        return (root, query, builder) -> {
            if (houseNumber != null && !houseNumber.isEmpty()) {
                return builder.like(builder.lower(root.get("houseNumber")),
                        "%" + houseNumber.toLowerCase() + "%");
            }
            return builder.conjunction();
        };
    }

    private static Specification<FamilyDetail> byAddress(String address) {
        return (root, query, builder) -> {
            if (address != null && !address.isEmpty()) {
                return builder.like(builder.lower(root.get("address")),
                        "%" + address.toLowerCase() + "%");
            }
            return builder.conjunction();
        };
    }
}
