package org.cn.web.admin.web.controller;

import com.cn.web.core.platform.exception.HandlerException;
import com.cn.web.core.platform.web.DefaultController;
import com.cn.web.core.platform.web.ResponseBuilder;
import com.cn.web.rbac.web.handler.PermissionHandler;
import com.cn.web.rbac.web.interceptor.PermissionRequired;
import com.cn.web.rbac.web.request.PermissionReq;
import com.cn.web.rbac.web.vo.PermissionBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "permission")
public class PermissionController extends DefaultController {

    @Autowired
    PermissionHandler permissionHandler;

    @PermissionRequired(value = "sys:permission:save")
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String save(@RequestBody PermissionReq req) {
        ResponseBuilder builder = ResponseBuilder.newBuilder();
        try {

            PermissionBean bean = permissionHandler.save(req);

            builder.result(bean);
            builder.message("success");
            builder.statusCode(200);
        } catch (HandlerException e) {
            builder.message(e.getMessage());
            builder.statusCode(e.getStatusCode());
        }
        return builder.buildJSONString();
    }

    @PermissionRequired(value = "sys:permission:update")
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(@RequestBody PermissionReq req) {
        ResponseBuilder builder = ResponseBuilder.newBuilder();
        try {

            permissionHandler.update(req);

            builder.message("success");
            builder.statusCode(200);
        } catch (HandlerException e) {
            builder.message(e.getMessage());
            builder.statusCode(e.getStatusCode());
        }
        return builder.buildJSONString();
    }

    @PermissionRequired(value = "sys:permission:delete")
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public String delete(String id) {
        ResponseBuilder builder = ResponseBuilder.newBuilder();
        try {

            permissionHandler.delete(id);

            builder.message("success");
            builder.statusCode(200);
        } catch (HandlerException e) {
            builder.message(e.getMessage());
            builder.statusCode(e.getStatusCode());
        }
        return builder.buildJSONString();
    }

    @PermissionRequired(value = "sys:permission:view")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(String type) {
        ResponseBuilder builder = ResponseBuilder.newBuilder();
        try {

            PermissionBean bean = permissionHandler.list(type);

            builder.result(bean != null ? bean.getChildren() : null);
            builder.message("success");
            builder.statusCode(200);
        } catch (HandlerException e) {
            builder.message(e.getMessage());
            builder.statusCode(e.getStatusCode());
        }
        return builder.buildJSONString();
    }

    @PermissionRequired(value = "sys:permission:view")
    @RequestMapping(value = "find", method = RequestMethod.GET)
    public String findById(String id) {
        ResponseBuilder builder = ResponseBuilder.newBuilder();
        try {

            PermissionBean bean = permissionHandler.findById(id);

            builder.result(bean);
            builder.message("success");
            builder.statusCode(200);
        } catch (HandlerException e) {
            builder.message(e.getMessage());
            builder.statusCode(e.getStatusCode());
        }
        return builder.buildJSONString();
    }

    @PermissionRequired(value = "sys:permission:view")
    @RequestMapping(value = "findOperation", method = RequestMethod.GET)
    public String findOperationPermissionByParentId(String parentId) {
        ResponseBuilder builder = ResponseBuilder.newBuilder();
        try {

            PermissionBean bean = permissionHandler.findOperationPermissionByParentId(parentId);

            builder.result(bean);
            builder.message("success");
            builder.statusCode(200);
        } catch (HandlerException e) {
            builder.message(e.getMessage());
            builder.statusCode(e.getStatusCode());
        }
        return builder.buildJSONString();
    }

    @PermissionRequired(value = "sys:permission:view")
    @RequestMapping(value = "findByUserId", method = RequestMethod.GET)
    public String findByUserId(String userId) {
        ResponseBuilder builder = ResponseBuilder.newBuilder();
        try {

            List<PermissionBean> list = permissionHandler.findByUserId(userId);

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
