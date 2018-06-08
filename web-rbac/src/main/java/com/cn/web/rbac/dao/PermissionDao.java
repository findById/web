package com.cn.web.rbac.dao;

import com.cn.web.rbac.domain.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("permissionDao")
public interface PermissionDao extends JpaRepository<Permission, String> {

    @Query(value = "SELECT * FROM permission AS p,role_permission AS rp,user_role AS ur WHERE p.id=rp.permission_id AND rp.role_id=ur.role_id AND ur.user_id=:userId AND p.del_flg=0 AND rp.del_flg=0 AND ur.del_flg=0 ORDER BY p.parent_id,p.position", nativeQuery = true)
    List<Permission> findPermissionsByUserId(@Param("userId") String userId);

    /**
     * 获取对于操作权限
     */
    @Query(value = "SELECT p.* FROM permission AS p WHERE p.type='operate' AND p.del_flag=0 AND p.parent_id=:parentId ORDER BY p.position", nativeQuery = true)
    List<Permission> findOperationPermissionByParentId(String parentId);
}