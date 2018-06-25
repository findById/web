package org.cn.web.admin.web.controller;

import com.cn.web.core.platform.web.DefaultController;
import com.cn.web.rbac.web.handler.PermissionHandler;
import com.cn.web.rbac.web.interceptor.PermissionRequired;
import com.cn.web.rbac.web.request.PermissionReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "permission")
public class PermissionController extends DefaultController {

    @Autowired
    PermissionHandler permissionHandler;

    @PermissionRequired(value = "sys:permission:save")
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String save(@RequestBody PermissionReq req) {
        return permissionHandler.save(req);
    }

    @PermissionRequired(value = "sys:permission:update")
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(@RequestBody PermissionReq req) {
        return permissionHandler.update(req);
    }

    @PermissionRequired(value = "sys:permission:delete")
    @RequestMapping(value = "delete")
    public String delete(String id) {
        return permissionHandler.delete(id);
    }

    @PermissionRequired(value = "sys:permission:view")
    @RequestMapping(value = "list")
    public String list() {
        return permissionHandler.list();
    }

    @PermissionRequired(value = "sys:permission:view")
    @RequestMapping(value = "find")
    public String findById(String id) {
        return permissionHandler.findById(id);
    }

    @PermissionRequired(value = "sys:permission:view")
    @RequestMapping(value = "findOperation")
    public String findOperationPermissionByParentId(String parentId) {
        return permissionHandler.findOperationPermissionByParentId(parentId);
    }

    @PermissionRequired(value = "sys:permission:view")
    @RequestMapping(value = "findByUserId")
    public String findByUserId(String userId) {
        return permissionHandler.findByUserId(userId);
    }
}
