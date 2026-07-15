package com.masjid.crm.specification;

import com.masjid.crm.dto.request.MarriageDetailRequest;
import com.masjid.crm.entity.MarriageDetail;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class MarriageDetailSpecification {

    public static Specification<MarriageDetail> filterMarriages(MarriageDetailRequest request) {
        return Specification
                .where(byDateOfMarriage(request.getDateOfMarriage()))
                .and(byCertificateNumber(request.getCertificateNumber()))
                .and(byMemberDetailId(request.getMemberDetailId()))
                .and(byFamilyDetailId(request.getFamilyDetailId()))
                .and(byPlaceOfNikkah(request.getPlaceOfNikkah()))
                .and(byGroomName(request.getGroomName()))
                .and(byBrideName(request.getBrideName()))
                .and(byFromDate(request.getFromDate()))
                .and(byToDate(request.getToDate()));
    }

    private static Specification<MarriageDetail> byDateOfMarriage(LocalDate dateOfMarriage) {
        return (root, query, builder) -> dateOfMarriage == null
                ? builder.conjunction()
                : builder.equal(root.get("dateOfMarriage"), dateOfMarriage);
    }

    private static Specification<MarriageDetail> byCertificateNumber(String certificateNumber) {
        return (root, query, builder) -> {
            if (certificateNumber != null && !certificateNumber.isEmpty()) {
                return builder.like(builder.lower(root.get("certificateNumber")),
                        "%" + certificateNumber.toLowerCase() + "%");
            }
            return builder.conjunction();
        };
    }

    private static Specification<MarriageDetail> byMemberDetailId(Long memberDetailId) {
        return (root, query, builder) -> memberDetailId == null
                ? builder.conjunction()
                : builder.equal(root.get("memberDetail").get("id"), memberDetailId);
    }

    private static Specification<MarriageDetail> byFamilyDetailId(Long familyDetailId) {
        return (root, query, builder) -> familyDetailId == null
                ? builder.conjunction()
                : builder.equal(root.get("memberDetail").get("familyDetail").get("id"), familyDetailId);
    }

    private static Specification<MarriageDetail> byPlaceOfNikkah(String placeOfNikkah) {
        return (root, query, builder) -> {
            if (placeOfNikkah != null && !placeOfNikkah.isEmpty()) {
                return builder.like(builder.lower(root.get("placeOfNikkah")),
                        "%" + placeOfNikkah.toLowerCase() + "%");
            }
            return builder.conjunction();
        };
    }

    private static Specification<MarriageDetail> byGroomName(String groomName) {
        return (root, query, builder) -> {
            if (groomName != null && !groomName.isEmpty()) {
                return builder.like(builder.lower(root.get("groomName")),
                        "%" + groomName.toLowerCase() + "%");
            }
            return builder.conjunction();
        };
    }

    private static Specification<MarriageDetail> byBrideName(String brideName) {
        return (root, query, builder) -> {
            if (brideName != null && !brideName.isEmpty()) {
                return builder.like(builder.lower(root.get("brideName")),
                        "%" + brideName.toLowerCase() + "%");
            }
            return builder.conjunction();
        };
    }

    private static Specification<MarriageDetail> byFromDate(LocalDate fromDate) {
        return (root, query, builder) -> fromDate == null
                ? builder.conjunction()
                : builder.greaterThanOrEqualTo(root.get("dateOfMarriage"), fromDate);
    }

    private static Specification<MarriageDetail> byToDate(LocalDate toDate) {
        return (root, query, builder) -> toDate == null
                ? builder.conjunction()
                : builder.lessThanOrEqualTo(root.get("dateOfMarriage"), toDate);
    }
}
