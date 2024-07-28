package com.masjid.crm.repository;

import com.masjid.crm.entity.MembershipDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MembershipRepository extends JpaRepository<MembershipDetail, Long>, JpaSpecificationExecutor<MembershipDetail> {
}
