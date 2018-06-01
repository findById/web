package com.cn.web.rbac.dao;

import com.cn.web.rbac.domain.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userRoleDao")
public interface UserRoleDao extends JpaRepository<UserRole, String> {

    @Modifying
    @Query(value = "DELETE user_role AS ur WHERE ur.user_id=:userId AND ur.role_id=:roleId", nativeQuery = true)
    void deleteUserRoleByUserIdAndRoleId(@Param("userId") String userId, @Param("roleId") String roleId);

    @Query(value = "SELECT ur.role_id FROM user_role AS ur WHERE ur.user_id=:userId", nativeQuery = true)
    List<String> findByUserId(@Param("userId") String userId);

}
