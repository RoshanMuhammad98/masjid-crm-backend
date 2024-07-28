package com.masjid.crm.repository;


import com.masjid.crm.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission,Long> {

    Permission findByPermission(String name);
}
