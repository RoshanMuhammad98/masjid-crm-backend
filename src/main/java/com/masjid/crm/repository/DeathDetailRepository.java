package com.masjid.crm.repository;

import com.masjid.crm.entity.DeathDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeathDetailRepository extends JpaRepository<DeathDetail, Long>, JpaSpecificationExecutor<DeathDetail> {

    List<DeathDetail> findByMemberDetail_FamilyDetail_Id(Long familyDetailId);
}
