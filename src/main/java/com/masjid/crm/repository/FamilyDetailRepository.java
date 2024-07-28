package com.masjid.crm.repository;

import com.masjid.crm.entity.FamilyDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FamilyDetailRepository extends JpaRepository<FamilyDetail, Long>, JpaSpecificationExecutor<FamilyDetail> {
}
