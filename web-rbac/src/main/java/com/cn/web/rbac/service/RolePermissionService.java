package com.cn.web.rbac.service;

import com.cn.web.rbac.domain.RolePermission;

import java.io.Serializable;
import java.util.List;

public interface RolePermissionService {
    RolePermission get(Serializable id);

    RolePermission save(RolePermission rolePermission);

    RolePermission update(RolePermission rolePermission);

    void updateRolePermission(String roleId, List<String> permIds);

    void delete(Serializable id);

    void deleteByLogic(Serializable[] ids);

    List<RolePermission> list();

    List<String> findPermissionIdListByRoleId(String roleId);

    void deleteByRoleIdAndPermissionId(String roleId, String permissionId);
}
