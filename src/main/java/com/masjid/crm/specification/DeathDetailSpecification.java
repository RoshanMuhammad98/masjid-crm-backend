package com.masjid.crm.specification;

import com.masjid.crm.dto.request.DeathDetailRequest;
import com.masjid.crm.entity.DeathDetail;
import com.masjid.crm.entity.MemberDetail;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

public class DeathDetailSpecification {

    public static Specification<DeathDetail> filterDeaths(DeathDetailRequest request) {

        Specification<DeathDetail> spec1 = byPlaceOfDeath(request.getPlaceOfDeath());
        Specification<DeathDetail> spec2 = byCauseOfDeath(request.getCauseOfDeath());
        Specification<DeathDetail> spec3 = byDeathCertificateNumber(request.getDeathCertificateNumber());
        Specification<DeathDetail> spec4 = byDateOfDeath(request.getDateFilter(), request.getFromDate(), request.getToDate());
        Specification<DeathDetail> spec5 = byMemberName(request.getMemberName());

        return Specification.where(spec1).and(spec2).and(spec3).and(spec4).and(spec5);
    }

    private static Specification<DeathDetail> byDateOfDeath(String dateFilter, LocalDate fromDate, LocalDate toDate) {

        String dateType = "createdDate";
        return (root, query, builder) -> {
            if (dateFilter.equals("All")) {
                return builder.conjunction();
            }

            else if (dateFilter.equals("Today")) {
                Path<Date> entityDate = root.get(dateType);
                Date startDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
                Date endDate = Date.from(LocalDate.now().atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant());
                return builder.between(entityDate, startDate, endDate);
            }
            else if (dateFilter.equals("Yesterday")) {
                Path<Date> entityDate = root.get(dateType);
                Date startDate = Date.from(LocalDate.now().minusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
                Date endDate = Date.from(LocalDate.now().minusDays(1).atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant());
                return builder.between(entityDate, startDate, endDate);
            }

            else if (dateFilter.equals("Choose Date")) {
                Path<Date> entityDate = root.get(dateType);
                Date startDate = Date.from(fromDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                Date endDate = Date.from(toDate.atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant());
                return builder.between(entityDate, startDate, endDate);
            }
            else if (dateFilter.equals("This Month")) {
                Path<Date> entityDate = root.get(dateType);

                Date startDate = Date.from(LocalDate.now().withDayOfMonth(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
                Date endDate = Date.from(LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()).atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant());
                return builder.between(entityDate, startDate, endDate);
            }
            else if (dateFilter.equals("Previous Month")) {
                Path<Date> entityDate = root.get(dateType);
                Date startDate = Date.from(LocalDate.now().minusMonths(1).withDayOfMonth(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
                Date endDate = Date.from(LocalDate.now().withDayOfMonth(1).minusDays(1).atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant());
                return builder.between(entityDate, startDate, endDate);
            }
            return builder.conjunction();
        };
    }

    private static Specification<DeathDetail> byMemberName(String memberName) {
        return (root, query, builder) -> {
            if (memberName != null && !memberName.isEmpty()) {
                Path<MemberDetail> memberDetailPath = root.get("memberDetail");
                return builder.like(builder.lower(memberDetailPath.get("name")), "%" + memberName.toLowerCase() + "%");
            }
            return builder.conjunction();
        };
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
