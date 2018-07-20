package com.cn.web.rbac.web.handler;

import com.cn.web.core.platform.exception.HandlerException;
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

    public PermissionBean save(PermissionReq req) {
        if (req == null) {
            throw new HandlerException(201, "Request body must not be null.");
        }
        if (req.getName() == null || req.getName().isEmpty()) {
            throw new HandlerException(201, "'name' must not be null.");
        }
        if (req.getType() == null || req.getType().isEmpty()) {
            throw new HandlerException(201, "'type' must not be null.");
        }
        if (req.getLink() == null || req.getLink().isEmpty()) {
            throw new HandlerException(201, "'link' must not be null.");
        }
        if (req.getParentId() == null || req.getParentId().isEmpty()) {
            throw new HandlerException(201, "'parentId' must not be null.");
        }

        Permission permission = new Permission();
        permission.setName(req.getName());
        permission.setType(req.getType());
        permission.setLink(req.getLink());
        permission.setIcon(req.getIcon());
        permission.setPermCode(req.getPermCode());
        permission.setParentId(req.getParentId());

        permissionService.save(permission);

        PermissionBean bean = new PermissionBean();
        BeanUtils.copyProperties(permission, bean);
        return bean;
    }

    public boolean update(PermissionReq req) {
        if (req == null || req.getId() == null) {
            throw new HandlerException(201, "Permission not exists");
        }

        Permission permission = permissionService.get(req.getId());
        if (permission == null) {
            throw new HandlerException(201, "Permission not exists");
        }

        if (req.getName() != null && !req.getName().isEmpty()) {
            permission.setName(req.getName());
        }
        if (req.getLink() != null && !req.getLink().isEmpty()) {
            permission.setLink(req.getLink());
        }
        if (req.getIcon() != null && !req.getIcon().isEmpty()) {
            permission.setIcon(req.getIcon());
        }
        if (req.getPermCode() != null && !req.getPermCode().isEmpty()) {
            permission.setPermCode(req.getPermCode());
        }
        if (req.getParentId() != null && !req.getParentId().isEmpty()) {
            permission.setParentId(req.getParentId());
        }

        permissionService.update(permission);

        return true;
    }

    public boolean delete(String id) {

        permissionService.delete(id);

        return true;
    }

    public PermissionBean list() {

        List<Permission> list = permissionService.list();

        return PermissionConverter.convertToTree(list);
    }

    public PermissionBean findById(String id) {

        Permission permission = permissionService.get(id);

        PermissionBean bean = new PermissionBean();
        BeanUtils.copyProperties(permission, bean);

        return bean;
    }

    public PermissionBean findOperationPermissionByParentId(String parentId) {

        List<Permission> list = permissionService.findOperationPermissionByParentId(parentId);

        return PermissionConverter.convertToTree(list);
    }

    public List<PermissionBean> findByUserId(String userId) {

        List<Permission> list = permissionService.findByUserId(userId);

        List<PermissionBean> result = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            for (Permission item : list) {
                PermissionBean bean = new PermissionBean();
                BeanUtils.copyProperties(item, bean);
                result.add(bean);
            }
        }

        return result;
    }
}
