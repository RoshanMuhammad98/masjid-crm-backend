package com.masjid.crm.repository;

import com.masjid.crm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmailAndPassword(String email, String password);

    /*@Query(value="SELECT u FROM Users u where Lower(email) LIKE Lower(:keyword)")
    List<User> findByUserKeyword(String keyword);*/

    Optional<User> findByUsername(String username);

    @Query(value = "SELECT u FROM User u Where u.role.name IN (:userRoles) ORDER BY u.name ASC ")
    List<User> findAllUsersByRole(List<String> userRoles);

    User findByPhoneNumber(String phoneNumber);

    User findByCodeAndPhoneNumber(String userCode, String phoneNumber);

    @Query(value = "SELECT u FROM User u Where u.role.name IN (:userRoles) AND u.active = true ORDER BY u.name ASC")
    List<User> findAllActiveUsersByRole(List<String> userRoles);

    @Query("SELECT MAX(u.code) FROM User u WHERE u.code LIKE :prefix%")
    String findMaxCodeByPrefix(String prefix);

    User findByCode(String agentCode);

    @Query("SELECT u FROM User u JOIN u.role r WHERE r.name IN (:roles) AND u.active = true")
    List<User> findAllActiveIntlTeamByRoleNames(List<String> roles);

    @Query("SELECT u FROM User u JOIN u.role r WHERE r.name IN (:roles)")
    List<User> findAllIntlTeamByRoleNames(List<String> roles);

    @Query(value = "SELECT u FROM User u Where u.code IN (:agentCodes) AND u.active = true ORDER BY u.name ASC ")
    List<User> findAllActiveUsersByCode(List<String> agentCodes);

    @Query(value = "SELECT u FROM User u Where u.code IN (:agentCodes) ORDER BY u.name ASC ")
    List<User> findAllUsersByCode(List<String> agentCodes);

    @Query(value = "SELECT u.code FROM User u Where u.role.name IN (:userRole) ORDER BY u.name ASC ")
    List<String> findAllUserCodesByRole(String userRole);

    User findByEmailAndActive(String email, boolean isActive);

}
