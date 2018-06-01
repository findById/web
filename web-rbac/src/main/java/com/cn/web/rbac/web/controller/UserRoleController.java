package com.cn.web.rbac.web.controller;

import com.alibaba.fastjson.JSON;
import com.cn.web.rbac.domain.UserRole;
import com.cn.web.rbac.service.UserRoleService;
import com.cn.web.rbac.web.interceptor.Logical;
import com.cn.web.rbac.web.interceptor.PermissionRequired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "userRole")
public class UserRoleController {

    @Autowired
    UserRoleService userRoleService;

    @PermissionRequired(value = {"sys:user:save", "sys:role:save"}, logical = Logical.AND)
    @RequestMapping(value = "save")
    public String save() {
        UserRole userRole = new UserRole();

        userRoleService.save(userRole);
        return JSON.toJSONString(userRole);
    }

    @PermissionRequired(value = {"sys:user:update", "sys:role:update"}, logical = Logical.AND)
    @RequestMapping(value = "update")
    public String update() {

        return "update";
    }

    @PermissionRequired(value = {"sys:user:delete", "sys:role:delete"}, logical = Logical.AND)
    @RequestMapping(value = "delete")
    public String delete(String id) {
        userRoleService.delete(id);
        return "ok";
    }

    @PermissionRequired(value = {"sys:user:view", "sys:role:view"}, logical = Logical.AND)
    @RequestMapping(value = "list")
    public String list() {
        List<UserRole> list = userRoleService.list();
        return JSON.toJSONString(list);
    }
}
