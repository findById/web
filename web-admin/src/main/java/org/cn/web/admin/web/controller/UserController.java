package org.cn.web.admin.web.controller;

import com.cn.web.core.platform.web.DefaultController;
import com.cn.web.core.platform.web.ResponseBuilder;
import com.cn.web.rbac.web.handler.UserHandler;
import com.cn.web.rbac.web.interceptor.PermissionRequired;
import com.cn.web.rbac.web.request.UserLoginReq;
import com.cn.web.rbac.web.request.UserReq;
import com.cn.web.rbac.web.request.UserUpdatePasswdReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "user")
public class UserController extends DefaultController {

    @Autowired
    UserHandler userHandler;

    @PermissionRequired(value = "sys:user:save")
    @RequestMapping(value = "save", method = {RequestMethod.POST})
    public String save(@RequestBody UserReq req) {
        return userHandler.save(req);
    }

    @PermissionRequired(value = "sys:user:update")
    @RequestMapping(value = "update", method = {RequestMethod.POST})
    public String update(@RequestBody UserReq req) {
        return userHandler.update(req);
    }

    @PermissionRequired(value = "sys:user:delete")
    @RequestMapping(value = "delete")
    public String delete(String ids) {
        return userHandler.delete(ids);
    }

    @PermissionRequired(value = "sys:user:view")
    @RequestMapping(value = "list")
    public String list(int page, /* @Max(20) */ int size) {
        return userHandler.list(page, size);
    }

    @PermissionRequired(value = "sys:user:view")
    @RequestMapping(value = "findById", method = {RequestMethod.GET})
    public String findById(String id) {
        return userHandler.findById(id);
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(@RequestBody UserLoginReq req) {
        return userHandler.login(req);
    }

    @RequestMapping(value = "updatePwd", method = {RequestMethod.POST})
    public String updatePassword(@RequestBody UserUpdatePasswdReq req) {
        return userHandler.updatePassword("", req);
    }

}
