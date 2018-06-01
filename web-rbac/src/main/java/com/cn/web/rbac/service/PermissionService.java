package com.cn.web.rbac.service;

import com.cn.web.rbac.domain.Permission;

import java.io.Serializable;
import java.util.List;

public interface PermissionService {
    Permission get(Serializable id);

    Permission save(Permission permission);

    Permission update(Permission permission);

    void delete(Serializable id);

    List<Permission> list();

    List<Permission> findByUserId(Serializable userId);

    void saveAll(List<Permission> permissions);

    void saveBaseOperation(String parentId, String name);

    List<Permission> findOperationPermissionByParentId(String parentId);
}
