package com.cn.web.rbac.web.controller;

import com.alibaba.fastjson.JSON;
import com.cn.web.rbac.domain.RolePermission;
import com.cn.web.rbac.service.RolePermissionService;
import com.cn.web.rbac.web.interceptor.Logical;
import com.cn.web.rbac.web.interceptor.PermissionRequired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "rolePermission")
public class RolePermissionController {

    @Autowired
    RolePermissionService rolePermissionService;

    @PermissionRequired(value = {"sys:role:save", "sys:permission:save"}, logical = Logical.AND)
    @RequestMapping(value = "save")
    public String save() {
        RolePermission rolePermission = new RolePermission();

        rolePermissionService.save(rolePermission);
        return JSON.toJSONString(rolePermission);
    }

    @PermissionRequired(value = {"sys:role:update", "sys:permission:update"}, logical = Logical.AND)
    @RequestMapping(value = "update")
    public String update() {

        return "update";
    }

    @PermissionRequired(value = {"sys:role:delete", "sys:permission:delete"}, logical = Logical.AND)
    @RequestMapping(value = "delete")
    public String delete(String id) {
        rolePermissionService.delete(id);
        return "ok";
    }

    @PermissionRequired(value = {"sys:role:view", "sys:permission:view"}, logical = Logical.AND)
    @RequestMapping(value = "list")
    public String list() {
        List<RolePermission> list = rolePermissionService.list();
        return JSON.toJSONString(list);
    }

}
