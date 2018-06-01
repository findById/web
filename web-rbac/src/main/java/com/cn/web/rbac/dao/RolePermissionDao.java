package com.cn.web.rbac.dao;

import com.cn.web.rbac.domain.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("rolePermissionDao")
public interface RolePermissionDao extends JpaRepository<RolePermission, String> {

    @Query(value = "SELECT rp.permission_id FROM role_permission AS rp WHERE rp.role_id=:roleId", nativeQuery = true)
    List<String> findPermissionIdListByRoleId(@Param("roleId") String roleId);

    @Modifying
    @Query(value = "DELETE role_permission AS rp WHERE rp.role_id=:roleId AND rp.permission_id=:permissionId", nativeQuery = true)
    void deleteByRoleIdAndPermissionId(@Param("roleId") String roleId, @Param("permissionId") String permissionId);

}
