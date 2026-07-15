package com.masjid.crm.repository;

import com.masjid.crm.entity.MarriageDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarriageRepository extends JpaRepository<MarriageDetail, Long>, JpaSpecificationExecutor<MarriageDetail> {

    List<MarriageDetail> findByMemberDetail_FamilyDetail_Id(Long familyDetailId);
}
