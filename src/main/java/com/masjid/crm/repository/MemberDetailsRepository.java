package com.masjid.crm.repository;

import com.masjid.crm.entity.MemberDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberDetailsRepository extends JpaRepository<MemberDetail, Long>, JpaSpecificationExecutor<MemberDetail> {
}
