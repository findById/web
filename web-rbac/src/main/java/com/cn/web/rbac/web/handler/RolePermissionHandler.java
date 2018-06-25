package com.cn.web.rbac.web.handler;

import com.cn.web.core.platform.web.ResponseBuilder;
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

    public String update(RolePermReq req) {
        ResponseBuilder.Builder builder = ResponseBuilder.newBuilder();

        if (req == null || req.getRoleId() == null || req.getRoleId().isEmpty()) {
            builder.statusCode(201);
            builder.message("'roleId' must not be null");
            return builder.buildJSONString();
        }
        if (req.getPermIds() == null || req.getPermIds().isEmpty()) {
            builder.statusCode(201);
            builder.message("'permIds' must not be null");
            return builder.buildJSONString();
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
        builder.statusCode(200);
        builder.message("success");
        return builder.buildJSONString();
    }

    public String list(String roleId) {
        ResponseBuilder.Builder builder = ResponseBuilder.newBuilder();

        List<String> roleIds = rolePermissionService.findPermissionIdListByRoleId(roleId);

        builder.result(roleIds);
        builder.statusCode(200);
        builder.message("success");
        return builder.buildJSONString();
    }
}
