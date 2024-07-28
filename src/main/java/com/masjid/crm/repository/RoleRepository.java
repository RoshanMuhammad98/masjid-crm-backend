package com.masjid.crm.repository;

import com.masjid.crm.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query(value="SELECT r FROM Role r where Lower(name) LIKE Lower(:keyword)")
    List<Role> findByRoleKeyword(String keyword);

    Role findByName(String name);
}
