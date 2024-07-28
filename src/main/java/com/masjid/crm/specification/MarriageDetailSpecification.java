package com.masjid.crm.specification;

import com.masjid.crm.dto.request.MarriageDetailRequest;
import com.masjid.crm.entity.MarriageDetail;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class MarriageDetailSpecification {

    public static Specification<MarriageDetail> filterMarriages(MarriageDetailRequest request) {

        Specification<MarriageDetail> spec1 = byDateOfMarriage(request.getDateOfMarriage());
        Specification<MarriageDetail> spec2 = byCertificateNumber(request.getCertificateNumber());
        Specification<MarriageDetail> spec3 = byMemberDetailId(request.getMemberDetailId());

        return Specification.where(spec1).and(spec2).and(spec3);
    }

    private static Specification<MarriageDetail> byDateOfMarriage(LocalDate dateOfMarriage) {
        return (root, query, builder) -> {
            if (dateOfMarriage != null) {
                return builder.equal(root.get("dateOfMarriage"), dateOfMarriage);
            }
            return builder.conjunction();
        };
    }

    private static Specification<MarriageDetail> byCertificateNumber(String certificateNumber) {
        return (root, query, builder) -> {
            if (certificateNumber != null && !certificateNumber.isEmpty()) {
                return builder.like(builder.lower(root.get("certificateNumber")), "%" + certificateNumber.toLowerCase() + "%");
            }
            return builder.conjunction();
        };
    }

    private static Specification<MarriageDetail> byMemberDetailId(Long memberDetailId) {
        return (root, query, builder) -> {
            if (memberDetailId != null) {
                return builder.equal(root.get("memberDetail").get("id"), memberDetailId);
            }
            return builder.conjunction();
        };
    }
}
