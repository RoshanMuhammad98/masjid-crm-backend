package com.masjid.crm.specification;

import com.masjid.crm.dto.request.MemberDetailRequest;
import com.masjid.crm.entity.MemberDetail;
import com.masjid.crm.model.Gender;
import com.masjid.crm.model.MartialStatus;
import org.springframework.data.jpa.domain.Specification;

public class MemberDetailSpecification {

    public static Specification<MemberDetail> filterMemberDetails(MemberDetailRequest request) {
        return Specification
                .where(byName(request.getName()))
                .and(byPhoneNumber(request.getPhoneNumber()))
                .and(byBloodGroup(request.getBloodGroup()))
                .and(byAlternativeNumber(request.getAlternativeNumber()))
                .and(byFamilyDetailId(request.getFamilyDetailId()))
                .and(byGender(request.getGender()))
                .and(byMartialStatus(request.getMartialStatus()))
                .and(byIsStudent(request.getIsStudent()))
                .and(byHasDisability(request.getHasDisability()))
                .and(byHasMedicalIssue(request.getHasMedicalIssue()))
                .and(byIsHead(request.getIsHead()))
                .and(byOccupation(request.getOccupation()));
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

    private static Specification<MemberDetail> byBloodGroup(String bloodGroup) {
        return (root, query, builder) -> {
            if (bloodGroup != null && !bloodGroup.isEmpty()) {
                return builder.like(builder.lower(root.get("bloodGroup")), "%" + bloodGroup.toLowerCase() + "%");
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

    private static Specification<MemberDetail> byFamilyDetailId(Long familyDetailId) {
        return (root, query, builder) -> familyDetailId == null
                ? builder.conjunction()
                : builder.equal(root.get("familyDetail").get("id"), familyDetailId);
    }

    private static Specification<MemberDetail> byGender(Gender gender) {
        return (root, query, builder) -> gender == null
                ? builder.conjunction()
                : builder.equal(root.get("gender"), gender);
    }

    private static Specification<MemberDetail> byMartialStatus(MartialStatus status) {
        return (root, query, builder) -> status == null
                ? builder.conjunction()
                : builder.equal(root.get("martialStatus"), status);
    }

    private static Specification<MemberDetail> byIsStudent(Boolean isStudent) {
        return (root, query, builder) -> isStudent == null
                ? builder.conjunction()
                : builder.equal(root.get("isStudent"), isStudent);
    }

    private static Specification<MemberDetail> byHasDisability(Boolean hasDisability) {
        return (root, query, builder) -> hasDisability == null
                ? builder.conjunction()
                : builder.equal(root.get("hasDisability"), hasDisability);
    }

    private static Specification<MemberDetail> byHasMedicalIssue(Boolean hasMedicalIssue) {
        return (root, query, builder) -> hasMedicalIssue == null
                ? builder.conjunction()
                : builder.equal(root.get("hasMedicalIssue"), hasMedicalIssue);
    }

    private static Specification<MemberDetail> byIsHead(Boolean isHead) {
        return (root, query, builder) -> isHead == null
                ? builder.conjunction()
                : builder.equal(root.get("isHead"), isHead);
    }

    private static Specification<MemberDetail> byOccupation(String occupation) {
        return (root, query, builder) -> {
            if (occupation != null && !occupation.isEmpty()) {
                return builder.like(builder.lower(root.get("occupation")), "%" + occupation.toLowerCase() + "%");
            }
            return builder.conjunction();
        };
    }
}
