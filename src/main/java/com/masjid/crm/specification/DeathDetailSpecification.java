package com.masjid.crm.specification;

import com.masjid.crm.dto.request.DeathDetailRequest;
import com.masjid.crm.entity.DeathDetail;
import org.springframework.data.jpa.domain.Specification;

public class DeathDetailSpecification {

    public static Specification<DeathDetail> filterDeaths(DeathDetailRequest request) {

        Specification<DeathDetail> spec1 = byPlaceOfDeath(request.getPlaceOfDeath());
        Specification<DeathDetail> spec2 = byCauseOfDeath(request.getCauseOfDeath());
        Specification<DeathDetail> spec3 = byDeathCertificateNumber(request.getDeathCertificateNumber());

        return Specification.where(spec1).and(spec2).and(spec3);
    }

    private static Specification<DeathDetail> byPlaceOfDeath(String placeOfDeath) {
        return (root, query, builder) -> {
            if (placeOfDeath != null && !placeOfDeath.isEmpty()) {
                return builder.like(builder.lower(root.get("placeOfDeath")), "%" + placeOfDeath.toLowerCase() + "%");
            }
            return builder.conjunction();
        };
    }

    private static Specification<DeathDetail> byCauseOfDeath(String causeOfDeath) {
        return (root, query, builder) -> {
            if (causeOfDeath != null && !causeOfDeath.isEmpty()) {
                return builder.like(builder.lower(root.get("causeOfDeath")), "%" + causeOfDeath.toLowerCase() + "%");
            }
            return builder.conjunction();
        };
    }

    private static Specification<DeathDetail> byDeathCertificateNumber(String deathCertificateNumber) {
        return (root, query, builder) -> {
            if (deathCertificateNumber != null && !deathCertificateNumber.isEmpty()) {
                return builder.like(builder.lower(root.get("deathCertificateNumber")), "%" + deathCertificateNumber.toLowerCase() + "%");
            }
            return builder.conjunction();
        };
    }

}
