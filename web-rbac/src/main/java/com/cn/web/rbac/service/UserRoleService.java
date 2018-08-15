package com.cn.web.rbac.service;

import com.cn.web.rbac.domain.UserRole;

import java.io.Serializable;
import java.util.List;

public interface UserRoleService {
    UserRole get(Serializable id);

    UserRole save(UserRole userRole);

    UserRole update(UserRole userRole);

    void updateUserRole(String userId, List<String> roleIds);

    void delete(Serializable id);

    List<UserRole> list();

    void deleteByUserIdAndRoleId(String userId, String roleId);

    List<String> findByUserId(String userId);
}
