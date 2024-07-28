package com.masjid.crm.specification;

import com.masjid.crm.dto.request.MemberDetailRequest;
import com.masjid.crm.entity.MemberDetail;
import org.springframework.data.jpa.domain.Specification;

public class MemberDetailSpecification {

    public static Specification<MemberDetail> filterMemberDetails(MemberDetailRequest request) {

        Specification<MemberDetail> spec1 = byName(request.getName());
        Specification<MemberDetail> spec2 = byPhoneNumber(request.getPhoneNumber());
        Specification<MemberDetail> spec3 = byBloodGroup(request.getBloodGroup());
        Specification<MemberDetail> spec4 = byAlternativeNumber(request.getBloodGroup());

        return Specification.where(spec1).and(spec2).and(spec3).and(spec4);
    }

    private static Specification<MemberDetail> byBloodGroup(String bloodGroup) {
        return (root, query, builder) -> {
            if (bloodGroup != null && !bloodGroup.isEmpty()) {
                return builder.like(builder.lower(root.get("bloodGroup")), "%" + bloodGroup.toLowerCase() + "%");
            }
            return builder.conjunction();
        };
    }

    private static Specification<MemberDetail> byName(String name) {
        return (root, query, builder) -> {
            if (name != null && !name.isEmpty()) {
                return builder.like(builder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
            }
            return builder.conjunction();
        };
    }

    private static Specification<MemberDetail> byPhoneNumber(String phoneNumber) {
        return (root, query, builder) -> {
            if (phoneNumber != null && !phoneNumber.isEmpty()) {
                return builder.like(builder.lower(root.get("phoneNumber")), "%" + phoneNumber.toLowerCase() + "%");
            }
            return builder.conjunction();
        };
    }

    private static Specification<MemberDetail> byAlternativeNumber(String alternativeNumber) {
        return (root, query, builder) -> {
            if (alternativeNumber != null && !alternativeNumber.isEmpty()) {
                return builder.like(builder.lower(root.get("alternativeNumber")), "%" + alternativeNumber.toLowerCase() + "%");
            }
            return builder.conjunction();
        };
    }

}
