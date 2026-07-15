package com.masjid.crm.repository;

import com.masjid.crm.entity.FinancialAccount;
import com.masjid.crm.model.FinancialSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FinancialAccountRepository extends JpaRepository<FinancialAccount, Long>, JpaSpecificationExecutor<FinancialAccount> {

    Optional<FinancialAccount> findBySourceTypeAndSourceId(FinancialSource sourceType, Long sourceId);
}
