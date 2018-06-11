package com.cn.web.rbac.web.controller;

import com.cn.web.core.platform.web.DefaultController;
import com.cn.web.core.platform.web.ResponseBuilder;
import com.cn.web.rbac.domain.RolePermission;
import com.cn.web.rbac.service.RolePermissionService;
import com.cn.web.rbac.web.interceptor.Logical;
import com.cn.web.rbac.web.interceptor.PermissionRequired;
import com.cn.web.rbac.web.request.RolePermReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "role/permission")
public class RolePermissionController extends DefaultController {

    @Autowired
    RolePermissionService rolePermissionService;

    @PermissionRequired(value = {"sys:role:update", "sys:permission:update"}, logical = Logical.AND)
    @RequestMapping(value = "update")
    public String update(@RequestBody RolePermReq req) {
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

    @PermissionRequired(value = {"sys:role:view", "sys:permission:view"}, logical = Logical.AND)
    @RequestMapping(value = "list")
    public String list(String roleId) {
        ResponseBuilder.Builder builder = ResponseBuilder.newBuilder();

        List<String> roleIds = rolePermissionService.findPermissionIdListByRoleId(roleId);

        builder.result(roleIds);
        builder.statusCode(200);
        builder.message("success");
        return builder.buildJSONString();
    }
}
