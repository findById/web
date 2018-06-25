package org.cn.web.admin.web.controller;

import com.cn.web.core.platform.web.DefaultController;
import com.cn.web.rbac.web.handler.UserRoleHandler;
import com.cn.web.rbac.web.interceptor.Logical;
import com.cn.web.rbac.web.interceptor.PermissionRequired;
import com.cn.web.rbac.web.request.UserRoleReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "user/role")
public class UserRoleController extends DefaultController {

    @Autowired
    UserRoleHandler userRoleHandler;

    @PermissionRequired(value = {"sys:user:update", "sys:role:update"}, logical = Logical.AND)
    @RequestMapping(value = "update")
    public String update(@RequestBody UserRoleReq req) {
        return userRoleHandler.update(req);
    }

    @PermissionRequired(value = {"sys:user:view", "sys:role:view"}, logical = Logical.AND)
    @RequestMapping(value = "list")
    public String list(String userId) {
        return userRoleHandler.list(userId);
    }
}
