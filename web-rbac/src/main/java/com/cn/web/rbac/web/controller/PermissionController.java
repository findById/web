package com.cn.web.rbac.web.controller;

import com.cn.web.core.platform.web.DefaultController;
import com.cn.web.core.platform.web.ResponseBuilder;
import com.cn.web.rbac.domain.Permission;
import com.cn.web.rbac.service.PermissionService;
import com.cn.web.rbac.web.converter.PermissionConverter;
import com.cn.web.rbac.web.interceptor.PermissionRequired;
import com.cn.web.rbac.web.request.PermissionReq;
import com.cn.web.rbac.web.vo.PermissionBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "permission")
public class PermissionController extends DefaultController {

    @Autowired
    PermissionService permissionService;

    @PermissionRequired(value = "sys:permission:save")
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String save(@RequestBody PermissionReq req) {
        ResponseBuilder.Builder builder = ResponseBuilder.newBuilder();

        if (req == null) {
            builder.statusCode(201);
            builder.message("Request body must not be null.");
            return builder.buildJSONString();
        }
        if (req.getName() == null || req.getName().isEmpty()) {
            builder.statusCode(201);
            builder.message("'name' must not be null.");
            return builder.buildJSONString();
        }
        if (req.getType() == null || req.getType().isEmpty()) {
            builder.statusCode(201);
            builder.message("'type' must not be null.");
            return builder.buildJSONString();
        }
        if (req.getLink() == null || req.getLink().isEmpty()) {
            builder.statusCode(201);
            builder.message("'link' must not be null.");
            return builder.buildJSONString();
        }
        if (req.getParentId() == null || req.getParentId().isEmpty()) {
            builder.statusCode(201);
            builder.message("'parentId' must not be null.");
            return builder.buildJSONString();
        }

        Permission permission = new Permission();
        permission.setName(req.getName());
        permission.setType(req.getType());
        permission.setLink(req.getLink());
        permission.setParentId(req.getParentId());

        permissionService.save(permission);

        PermissionBean bean = new PermissionBean();
        BeanUtils.copyProperties(permission, bean);

        builder.statusCode(200);
        builder.message("ok");
        builder.result(bean);
        return builder.buildJSONString();
    }

    @PermissionRequired(value = "sys:permission:update")
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(@RequestBody PermissionReq req) {
        ResponseBuilder.Builder builder = ResponseBuilder.newBuilder();

        if (req == null || req.getId() == null) {
            builder.statusCode(201);
            builder.message("Permission not exists");
            return builder.buildJSONString();
        }

        Permission permission = permissionService.get(req.getId());
        if (permission == null) {
            builder.statusCode(201);
            builder.message("Permission not exists");
            return builder.buildJSONString();
        }

        if (req.getName() != null && !req.getName().isEmpty()) {
            permission.setName(req.getName());
        }
        if (req.getLink() != null && !req.getLink().isEmpty()) {
            permission.setLink(req.getLink());
        }
        if (req.getParentId() != null && !req.getParentId().isEmpty()) {
            permission.setParentId(req.getParentId());
        }

        permissionService.update(permission);

        builder.statusCode(200);
        builder.message("success");
        return builder.buildJSONString();
    }

    @PermissionRequired(value = "sys:permission:delete")
    @RequestMapping(value = "delete")
    public String delete(String id) {
        ResponseBuilder.Builder builder = ResponseBuilder.newBuilder();

        permissionService.delete(id);

        builder.statusCode(200);
        builder.message("ok");
        return builder.buildJSONString();
    }

    @PermissionRequired(value = "sys:permission:view")
    @RequestMapping(value = "list")
    public String list() {
        ResponseBuilder.Builder builder = ResponseBuilder.newBuilder();

        List<Permission> list = permissionService.list();

        PermissionBean result = PermissionConverter.convertToMenu(list);

        builder.statusCode(200);
        builder.message("success");
        builder.result(result);
        return builder.buildJSONString();
    }

    @PermissionRequired(value = "sys:permission:view")
    @RequestMapping(value = "find")
    public String findById(String id) {
        ResponseBuilder.Builder builder = ResponseBuilder.newBuilder();

        Permission permission = permissionService.get(id);

        PermissionBean bean = new PermissionBean();
        BeanUtils.copyProperties(permission, bean);

        builder.statusCode(200);
        builder.message("success");
        builder.result(bean);
        return builder.buildJSONString();
    }

    @PermissionRequired(value = "sys:permission:view")
    @RequestMapping(value = "findOperation")
    public String findOperationPermissionByParentId(String parentId) {
        ResponseBuilder.Builder builder = ResponseBuilder.newBuilder();

        List<Permission> list = permissionService.findOperationPermissionByParentId(parentId);

        PermissionBean result = PermissionConverter.convertToMenu(list);

        builder.statusCode(200);
        builder.message("success");
        builder.result(result);
        return builder.buildJSONString();
    }

    @PermissionRequired(value = "sys:permission:view")
    @RequestMapping(value = "findByUserId")
    public String findByUserId(String userId) {
        ResponseBuilder.Builder builder = ResponseBuilder.newBuilder();

        List<Permission> list = permissionService.findByUserId(userId);

        List<PermissionBean> result = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            for (Permission item : list) {
                PermissionBean bean = new PermissionBean();
                BeanUtils.copyProperties(item, bean);
                result.add(bean);
            }
        }

        builder.statusCode(200);
        builder.message("success");
        builder.result(result);
        return builder.buildJSONString();
    }
}
