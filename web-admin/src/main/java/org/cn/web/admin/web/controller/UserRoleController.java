package org.cn.web.admin.web.controller;

import com.cn.web.core.platform.exception.HandlerException;
import com.cn.web.core.platform.web.DefaultController;
import com.cn.web.core.platform.web.ResponseBuilder;
import com.cn.web.rbac.web.handler.UserRoleHandler;
import com.cn.web.rbac.web.interceptor.Logical;
import com.cn.web.rbac.web.interceptor.PermissionRequired;
import com.cn.web.rbac.web.request.UserRoleReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "user/role")
public class UserRoleController extends DefaultController {

    @Autowired
    UserRoleHandler userRoleHandler;

    @PermissionRequired(value = {"sys:user:update", "sys:role:update"}, logical = Logical.AND)
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(@RequestBody UserRoleReq req) {
        ResponseBuilder.Builder builder = ResponseBuilder.newBuilder();
        try {

            userRoleHandler.update(req);

            builder.message("success");
            builder.statusCode(200);
        } catch (HandlerException e) {
            builder.message(e.getMessage());
            builder.statusCode(e.getStatusCode());
        }
        return builder.buildJSONString();
    }

    @PermissionRequired(value = {"sys:user:view", "sys:role:view"}, logical = Logical.AND)
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(String userId) {
        ResponseBuilder.Builder builder = ResponseBuilder.newBuilder();
        try {

            List<String> list = userRoleHandler.list(userId);

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
