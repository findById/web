package com.cn.web.rbac.web.handler;

import com.cn.web.core.platform.exception.HandlerException;
import com.cn.web.rbac.domain.BaseEntity;
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
//        if (req.getLink() == null || req.getLink().isEmpty()) {
//            if (!PermissionConverter.TYPE_DIR.equals(req.getType())) {
//                throw new HandlerException(201, "'link' must not be null.");
//            }
//        }
        if (req.getParentId() == null || req.getParentId().isEmpty()) {
            throw new HandlerException(201, "'parentId' must not be null.");
        }

        Permission permission = new Permission();
        permission.setName(req.getName());
        permission.setType(req.getType());
        permission.setPosition(req.getPosition() != null ? req.getPosition() : 0);
        permission.setLink(req.getLink());
        permission.setPermCode(req.getPermCode());
        permission.setMethod(req.getMethod());
        permission.setIcon(req.getIcon());
        permission.setVisible(req.getVisible() != null ? req.getVisible() : 0);

        Permission parent = permissionService.get(req.getParentId());
        if (parent == null) {
            throw new HandlerException(201, "Parent permission not exists");
        }
        permission.setParentId(parent.getId());

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
        if (req.getType() != null && !req.getType().isEmpty()) {
            permission.setType(req.getType());
        }
        if (req.getPosition() != null) {
            permission.setPosition(req.getPosition());
        }
        if (req.getLink() != null && !req.getLink().isEmpty()) {
            permission.setLink(req.getLink());
        }
        if (req.getPermCode() != null && !req.getPermCode().isEmpty()) {
            permission.setPermCode(req.getPermCode());
        }
        if (req.getMethod() != null && !req.getMethod().isEmpty()) {
            permission.setMethod(req.getMethod());
        }
        if (req.getIcon() != null && !req.getIcon().isEmpty()) {
            permission.setIcon(req.getIcon());
        }
        if (req.getVisible() != null) {
            permission.setVisible(req.getVisible());
        }
        if (req.getParentId() != null && !req.getParentId().isEmpty()) {
            Permission parent = permissionService.get(req.getParentId());
            if (parent == null) {
                throw new HandlerException(201, "Parent permission not exists");
            }
            permission.setParentId(req.getParentId());
        }

        permissionService.update(permission);

        return true;
    }

    public boolean delete(String id) {
        permissionService.delete(id);
        return true;
    }

    public boolean deleteByLogic(String id) {
        permissionService.deleteByLogic(id);
        return true;
    }

    public List<Permission> list() {
        return permissionService.list();
    }

    public List<PermissionBean> findByParentId(String parentId) {
        List<Permission> list = permissionService.findByParentId(parentId);
        if (list == null) {
            return null;
        }
        List<PermissionBean> beans = new ArrayList<>();
        for (Permission item : list) {
            PermissionBean bean = new PermissionBean();
            BeanUtils.copyProperties(item, bean);
            beans.add(bean);
        }
        return beans;
    }

    public PermissionBean findById(String id) {
        Permission permission = permissionService.get(id);
        if (permission == null || permission.getDelFlg() == BaseEntity.FLAG_DELETE) {
            throw new HandlerException(201, "permission not exists");
        }
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
                if (item.getDelFlg() == BaseEntity.FLAG_DELETE) {
                    continue;
                }
                PermissionBean bean = new PermissionBean();
                BeanUtils.copyProperties(item, bean);
                result.add(bean);
            }
        }

        return result;
    }
}
