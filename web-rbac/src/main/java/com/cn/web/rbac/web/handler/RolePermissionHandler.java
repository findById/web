package com.cn.web.rbac.web.handler;

import com.cn.web.core.platform.exception.HandlerException;
import com.cn.web.rbac.service.RolePermissionService;
import com.cn.web.rbac.web.request.RolePermReq;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RolePermissionHandler {

    @Autowired
    RolePermissionService rolePermissionService;

    public boolean update(RolePermReq req) {

        if (req == null || req.getRoleId() == null || req.getRoleId().isEmpty()) {
            throw new HandlerException(201, "'roleId' must not be null.");
        }
        if (req.getPermIds() == null || req.getPermIds().isEmpty()) {
            throw new HandlerException(201, "'permIds' must not be null");
        }

        // clear permission cache first
        // TODO Clear permission cache

        rolePermissionService.updateRolePermission(req.getRoleId(), req.getPermIds());
        return true;
    }

    public List<String> list(String roleId) {
        return rolePermissionService.findPermissionIdListByRoleId(roleId);
    }
}
