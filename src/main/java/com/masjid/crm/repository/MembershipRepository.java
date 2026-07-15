package com.masjid.crm.repository;

import com.masjid.crm.entity.MembershipDetail;
import com.masjid.crm.model.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MembershipRepository extends JpaRepository<MembershipDetail, Long>, JpaSpecificationExecutor<MembershipDetail> {

    @Query("SELECT m FROM MembershipDetail m WHERE m.paymentStatus = :status AND m.createdDate <= :before ORDER BY m.createdDate ASC")
    List<MembershipDetail> findByPaymentStatusAndCreatedBefore(@Param("status") PaymentStatus status,
                                                               @Param("before") Date before);
}
