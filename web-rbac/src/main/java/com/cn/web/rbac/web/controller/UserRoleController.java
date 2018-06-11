package com.cn.web.rbac.web.controller;

import com.cn.web.core.platform.web.DefaultController;
import com.cn.web.core.platform.web.ResponseBuilder;
import com.cn.web.rbac.domain.UserRole;
import com.cn.web.rbac.service.UserRoleService;
import com.cn.web.rbac.web.interceptor.Logical;
import com.cn.web.rbac.web.interceptor.PermissionRequired;
import com.cn.web.rbac.web.request.UserRoleReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "user/role")
public class UserRoleController extends DefaultController {

    @Autowired
    UserRoleService userRoleService;

    @PermissionRequired(value = {"sys:user:update", "sys:role:update"}, logical = Logical.AND)
    @RequestMapping(value = "update")
    public String update(@RequestBody UserRoleReq req) {
        ResponseBuilder.Builder builder = ResponseBuilder.newBuilder();

        if (req == null || req.getUserId() == null || req.getUserId().isEmpty()) {
            builder.statusCode(201);
            builder.message("'userId' must not be null");
            return builder.buildJSONString();
        }
        if (req.getRoleIds() == null || req.getRoleIds().isEmpty()) {
            builder.statusCode(201);
            builder.message("'roleIds' must not be null");
            return builder.buildJSONString();
        }

        List<String> oldRoleIds = userRoleService.findByUserId(req.getUserId());
        List<String> newRoleIds = req.getRoleIds();

        // clear permission cache first
        // TODO Clear permission cache

        // need to delete?
        if (oldRoleIds == null) {
            oldRoleIds = new ArrayList<>(0);
        }
        for (String roleId : oldRoleIds) {
            if (!newRoleIds.contains(roleId)) {
                userRoleService.deleteByUserIdAndRoleId(req.getUserId(), roleId);
            }
        }
        // need to save?
        for (String roleId : newRoleIds) {
            if (!oldRoleIds.contains(roleId)) {
                UserRole userRole = new UserRole();
                userRole.setUserId(req.getUserId());
                userRole.setRoleId(roleId);
                userRoleService.save(userRole);
            }
        }
        builder.statusCode(200);
        builder.message("success");
        return builder.buildJSONString();
    }

    @PermissionRequired(value = {"sys:user:view", "sys:role:view"}, logical = Logical.AND)
    @RequestMapping(value = "list")
    public String list(String userId) {
        ResponseBuilder.Builder builder = ResponseBuilder.newBuilder();

        List<String> roleIds = userRoleService.findByUserId(userId);

        builder.result(roleIds);
        builder.statusCode(200);
        builder.message("success");
        return builder.buildJSONString();
    }
}
