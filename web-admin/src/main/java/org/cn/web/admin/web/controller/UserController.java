package org.cn.web.admin.web.controller;

import com.cn.web.core.platform.exception.HandlerException;
import com.cn.web.core.platform.web.DefaultController;
import com.cn.web.core.platform.web.ResponseBuilder;
import com.cn.web.rbac.web.handler.UserHandler;
import com.cn.web.rbac.web.interceptor.PermissionRequired;
import com.cn.web.rbac.web.request.UserDeleteReq;
import com.cn.web.rbac.web.request.UserLoginReq;
import com.cn.web.rbac.web.request.UserReq;
import com.cn.web.rbac.web.request.UserUpdatePasswdReq;
import com.cn.web.rbac.web.vo.UserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import java.util.HashMap;

@RestController
@RequestMapping(value = "user")
public class UserController extends DefaultController {

    @Autowired
    UserHandler userHandler;

    @PermissionRequired(value = "sys:user:save")
    @RequestMapping(value = "save", method = {RequestMethod.POST})
    public String save(@RequestBody UserReq req) {
        ResponseBuilder builder = ResponseBuilder.newBuilder();
        try {

            UserBean bean = userHandler.save(req);

            builder.result(bean);
            builder.message("success");
            builder.statusCode(200);
        } catch (HandlerException e) {
            builder.message(e.getMessage());
            builder.statusCode(e.getStatusCode());
        }
        return builder.buildJSONString();
    }

    @PermissionRequired(value = "sys:user:update")
    @RequestMapping(value = "update", method = {RequestMethod.POST})
    public String update(@RequestBody UserReq req) {
        ResponseBuilder builder = ResponseBuilder.newBuilder();
        try {

            userHandler.update(req);

            builder.message("success");
            builder.statusCode(200);
        } catch (HandlerException e) {
            builder.message(e.getMessage());
            builder.statusCode(e.getStatusCode());
        }
        return builder.buildJSONString();
    }

    @PermissionRequired(value = "sys:user:delete")
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public String delete(@RequestBody UserDeleteReq req) {
        ResponseBuilder builder = ResponseBuilder.newBuilder();
        try {
            if (req == null || req.getIds() == null) {
                builder.message("'ids' must not be null");
                builder.statusCode(400);
                return builder.buildJSONString();
            }

            userHandler.delete(req.getIds());

            builder.message("success");
            builder.statusCode(200);
        } catch (HandlerException e) {
            builder.message(e.getMessage());
            builder.statusCode(e.getStatusCode());
        }
        return builder.buildJSONString();
    }

    @PermissionRequired(value = "sys:user:view")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(@RequestParam(name = "page", defaultValue = "1") int page,
                       @RequestParam(name = "size", defaultValue = "20") @Max(50) int size,
                       @RequestParam(name = "keywords", required = false) String keywords) {
        ResponseBuilder builder = ResponseBuilder.newBuilder();
        try {
            HashMap<String, Object> result;
            if (!StringUtils.isEmpty(keywords)) {
                result = userHandler.search(keywords, page, size);
            } else {
                result = userHandler.list(page, size);
            }

            builder.result(result);
            builder.message("success");
            builder.statusCode(200);
        } catch (HandlerException e) {
            builder.message(e.getMessage());
            builder.statusCode(e.getStatusCode());
        }
        return builder.buildJSONString();
    }

    @PermissionRequired(value = {"owner", "sys:user:view"})
    @RequestMapping(value = "info", method = {RequestMethod.GET})
    public String getUserInfo(@RequestHeader("userId") String userId) {
        return findById(userId);
    }

    @PermissionRequired(value = "sys:user:view")
    @RequestMapping(value = "findById", method = {RequestMethod.GET})
    public String findById(String userId) {
        ResponseBuilder builder = ResponseBuilder.newBuilder();
        try {
            if (StringUtils.isEmpty(userId)) {
                builder.message("'userId' must not be null");
                builder.statusCode(400);
                return builder.buildJSONString();
            }

            UserBean bean = userHandler.findById(userId);

            builder.result(bean);
            builder.message("success");
            builder.statusCode(200);
        } catch (HandlerException e) {
            builder.message(e.getMessage());
            builder.statusCode(e.getStatusCode());
        }
        return builder.buildJSONString();
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(@RequestBody UserLoginReq req) {
        ResponseBuilder builder = ResponseBuilder.newBuilder();
        try {

            UserBean bean = userHandler.login(req);

            builder.result(bean);
            builder.message("success");
            builder.statusCode(200);
        } catch (HandlerException e) {
            builder.message(e.getMessage());
            builder.statusCode(e.getStatusCode());
        }
        return builder.buildJSONString();
    }

    @PermissionRequired(value = {"owner", "sys:user:password"})
    @RequestMapping(value = "updatePwd", method = {RequestMethod.POST})
    public String updatePassword(@RequestHeader("userId") String userId, @RequestBody UserUpdatePasswdReq req) {
        ResponseBuilder builder = ResponseBuilder.newBuilder();
        try {

            userHandler.updatePassword(userId, req);

            builder.message("success");
            builder.statusCode(200);
        } catch (HandlerException e) {
            builder.message(e.getMessage());
            builder.statusCode(e.getStatusCode());
        }
        return builder.buildJSONString();
    }

}
