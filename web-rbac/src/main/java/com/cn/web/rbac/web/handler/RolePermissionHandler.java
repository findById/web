package com.cn.web.rbac.web.handler;

import com.cn.web.core.platform.exception.HandlerException;
import com.cn.web.rbac.domain.RolePermission;
import com.cn.web.rbac.service.RolePermissionService;
import com.cn.web.rbac.web.request.RolePermReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("rolePermissionHandler")
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

        List<String> oldPermIdList = rolePermissionService.findPermissionIdListByRoleId(req.getRoleId());
        List<String> newPermIds = req.getPermIds();

        // clear permission cache first
        // TODO Clear permission cache

        // need to delete?
        if (oldPermIdList == null) {
            oldPermIdList = new ArrayList<>(0);
        }
        for (String permId : oldPermIdList) {
            if (!newPermIds.contains(permId)) {
                rolePermissionService.deleteByRoleIdAndPermissionId(req.getRoleId(), permId);
            }
        }
        // need to save?
        for (String permId : newPermIds) {
            if (!oldPermIdList.contains(permId)) {
                RolePermission rolePermission = new RolePermission();
                rolePermission.setRoleId(req.getRoleId());
                rolePermission.setPermissionId(permId);
                rolePermissionService.save(rolePermission);
            }
        }
        return true;
    }

    public List<String> list(String roleId) {
        return rolePermissionService.findPermissionIdListByRoleId(roleId);
    }
}
