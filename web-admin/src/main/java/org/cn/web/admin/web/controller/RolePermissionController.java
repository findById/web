package org.cn.web.admin.web.controller;

import com.cn.web.core.platform.web.DefaultController;
import com.cn.web.rbac.web.handler.RolePermissionHandler;
import com.cn.web.rbac.web.interceptor.Logical;
import com.cn.web.rbac.web.interceptor.PermissionRequired;
import com.cn.web.rbac.web.request.RolePermReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "role/permission")
public class RolePermissionController extends DefaultController {

    @Autowired
    RolePermissionHandler rolePermissionHandler;

    @PermissionRequired(value = {"sys:role:update", "sys:permission:update"}, logical = Logical.AND)
    @RequestMapping(value = "update")
    public String update(@RequestBody RolePermReq req) {
        return rolePermissionHandler.update(req);
    }

    @PermissionRequired(value = {"sys:role:view", "sys:permission:view"}, logical = Logical.AND)
    @RequestMapping(value = "list")
    public String list(String roleId) {
        return rolePermissionHandler.list(roleId);
    }
}
