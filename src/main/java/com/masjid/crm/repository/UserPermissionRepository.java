package com.masjid.crm.repository;

import com.masjid.crm.entity.Role;
import com.masjid.crm.entity.UserPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPermissionRepository extends JpaRepository<UserPermission, Long> {

    List<UserPermission> findByRole(Role role);
}
