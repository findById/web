package com.cn.web.rbac.web.handler;

import com.cn.web.core.platform.exception.HandlerException;
import com.cn.web.rbac.service.UserRoleService;
import com.cn.web.rbac.web.request.UserRoleReq;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserRoleHandler {

    @Autowired
    UserRoleService userRoleService;

    public boolean update(UserRoleReq req) {

        if (req == null || req.getUserId() == null || req.getUserId().isEmpty()) {
            throw new HandlerException(201, "'userId' must not be null");
        }
        if (req.getRoleIds() == null || req.getRoleIds().isEmpty()) {
            throw new HandlerException(201, "'roleIds' must not be null");
        }

        // clear permission cache first
        // TODO Clear permission cache

        userRoleService.updateUserRole(req.getUserId(), req.getRoleIds());
        return true;
    }

    public List<String> list(String userId) {
        return userRoleService.findByUserId(userId);
    }
}
