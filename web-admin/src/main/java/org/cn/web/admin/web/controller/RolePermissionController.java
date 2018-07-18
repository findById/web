package org.cn.web.admin.web.controller;

import com.cn.web.core.platform.exception.HandlerException;
import com.cn.web.core.platform.web.DefaultController;
import com.cn.web.core.platform.web.ResponseBuilder;
import com.cn.web.rbac.web.handler.RolePermissionHandler;
import com.cn.web.rbac.web.interceptor.Logical;
import com.cn.web.rbac.web.interceptor.PermissionRequired;
import com.cn.web.rbac.web.request.RolePermReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "role/permission")
public class RolePermissionController extends DefaultController {

    @Autowired
    RolePermissionHandler rolePermissionHandler;

    @PermissionRequired(value = {"sys:role:update", "sys:permission:update"}, logical = Logical.AND)
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(@RequestBody RolePermReq req) {
        ResponseBuilder builder = ResponseBuilder.newBuilder();
        try {

            rolePermissionHandler.update(req);

            builder.message("success");
            builder.statusCode(200);
        } catch (HandlerException e) {
            builder.message(e.getMessage());
            builder.statusCode(e.getStatusCode());
        }
        return builder.buildJSONString();
    }

    @PermissionRequired(value = {"sys:role:view", "sys:permission:view"}, logical = Logical.AND)
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(String roleId) {
        ResponseBuilder builder = ResponseBuilder.newBuilder();
        try {

            List<String> list = rolePermissionHandler.list(roleId);

            builder.result(list);
            builder.message("success");
            builder.statusCode(200);
        } catch (HandlerException e) {
            builder.message(e.getMessage());
            builder.statusCode(e.getStatusCode());
        }
        return builder.buildJSONString();
    }
}
