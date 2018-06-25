package org.cn.web.admin.web.controller;

import com.cn.web.core.platform.web.DefaultController;
import com.cn.web.rbac.web.handler.RoleHandler;
import com.cn.web.rbac.web.interceptor.PermissionRequired;
import com.cn.web.rbac.web.request.RoleReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "role")
public class RoleController extends DefaultController {

    @Autowired
    RoleHandler roleHandler;

    @PermissionRequired("sys:role:save")
    @RequestMapping(value = "save", method = {RequestMethod.POST})
    public String save(@RequestBody RoleReq req) {
        return roleHandler.save(req);
    }

    @PermissionRequired("sys:role:update")
    @RequestMapping(value = "update", method = {RequestMethod.POST})
    public String update(@RequestBody RoleReq req) {
        return roleHandler.update(req);
    }

    @PermissionRequired("sys:role:delete")
    @RequestMapping(value = "delete")
    public String delete(String id) {
        return roleHandler.delete(id);
    }

    @PermissionRequired("sys:role:view")
    @RequestMapping(value = "list")
    public String list(int page, /* @Max(20) */ int size) {
        return roleHandler.list(page, size);
    }
}
