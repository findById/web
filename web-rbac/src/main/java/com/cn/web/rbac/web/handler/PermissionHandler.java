package com.cn.web.rbac.web.handler;

import com.cn.web.core.platform.web.ResponseBuilder;
import com.cn.web.rbac.domain.Permission;
import com.cn.web.rbac.service.PermissionService;
import com.cn.web.rbac.web.converter.PermissionConverter;
import com.cn.web.rbac.web.request.PermissionReq;
import com.cn.web.rbac.web.vo.PermissionBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("permissionHandler")
public class PermissionHandler {

    @Autowired
    PermissionService permissionService;

    public String save(PermissionReq req) {
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

    public String update(PermissionReq req) {
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

    public String delete(String id) {
        ResponseBuilder.Builder builder = ResponseBuilder.newBuilder();

        permissionService.delete(id);

        builder.statusCode(200);
        builder.message("ok");
        return builder.buildJSONString();
    }

    public String list() {
        ResponseBuilder.Builder builder = ResponseBuilder.newBuilder();

        List<Permission> list = permissionService.list();

        PermissionBean result = PermissionConverter.convertToMenu(list);

        builder.statusCode(200);
        builder.message("success");
        builder.result(result);
        return builder.buildJSONString();
    }

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

    public String findOperationPermissionByParentId(String parentId) {
        ResponseBuilder.Builder builder = ResponseBuilder.newBuilder();

        List<Permission> list = permissionService.findOperationPermissionByParentId(parentId);

        PermissionBean result = PermissionConverter.convertToMenu(list);

        builder.statusCode(200);
        builder.message("success");
        builder.result(result);
        return builder.buildJSONString();
    }

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
