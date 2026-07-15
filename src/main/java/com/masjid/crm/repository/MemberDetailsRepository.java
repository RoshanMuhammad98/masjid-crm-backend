package com.masjid.crm.repository;

import com.masjid.crm.entity.MemberDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberDetailsRepository extends JpaRepository<MemberDetail, Long>, JpaSpecificationExecutor<MemberDetail> {

    List<MemberDetail> findByFamilyDetailId(Long familyDetailId);

    List<MemberDetail> findByHasDisabilityTrue();

    List<MemberDetail> findByHasMedicalIssueTrue();

    java.util.Optional<MemberDetail> findFirstByFamilyDetailIdAndIsHeadTrue(Long familyDetailId);
}
