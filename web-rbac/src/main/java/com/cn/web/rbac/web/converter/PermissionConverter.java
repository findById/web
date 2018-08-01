package com.cn.web.rbac.web.converter;

import com.cn.web.rbac.domain.Permission;
import com.cn.web.rbac.web.vo.PermissionBean;

import java.util.ArrayList;
import java.util.List;

public class PermissionConverter {
    public static final String TYPE_DIR = "0";
    public static final String TYPE_MENU = "1";
    public static final String TYPE_OPERATE = "2";

    public static PermissionBean convertToTree(List<Permission> permissionList) {
        PermissionBean bean = null;
        for (Permission permission : permissionList) {
            bean = convertToTree(bean, permission);
        }
        return bean;
    }

    private static PermissionBean convertToTree(PermissionBean bean, Permission permission) {
        if (bean == null) { // root node
            bean = new PermissionBean();
            bean.setId(permission.getId());
            bean.setName(permission.getName());
            bean.setPosition(permission.getPosition());
            bean.setType(permission.getType());
            bean.setLink(permission.getLink());
            bean.setPermCode(permission.getPermCode());
            bean.setMethod(permission.getMethod());
            bean.setIcon(permission.getIcon());
            bean.setParentId(permission.getParentId());
            bean.setState(permission.getState()); // opened
        } else {
            String cId = bean.getId();
            if (permission.getParentId() != null && cId.equals(permission.getParentId())) {
                PermissionBean spb = new PermissionBean();
                spb.setId(permission.getId());
                spb.setName(permission.getName());
                spb.setPosition(permission.getPosition());
                spb.setType(permission.getType());
                spb.setLink(permission.getLink());
                spb.setPermCode(permission.getPermCode());
                spb.setMethod(permission.getMethod());
                spb.setIcon(permission.getIcon());
                spb.setParentId(permission.getParentId());
                spb.setState(permission.getState()); // closed
                if (bean.getChildren() == null) {
                    bean.setChildren(new ArrayList<>());
                }
                bean.getChildren().add(spb);
            } else {
                for (PermissionBean children : bean.getChildren()) {
                    convertToTree(children, permission);
                }
            }
        }
        return bean;
    }

    public static PermissionBean convertToMenu(List<Permission> permissionList) {
        PermissionBean bean = null;
        for (Permission permission : permissionList) {
            bean = convertToMenu(bean, permission);
        }
        return bean;
    }

    private static PermissionBean convertToMenu(PermissionBean bean, Permission permission) {
        if (bean == null) { // root node
            bean = new PermissionBean();
            bean.setId(permission.getId());
            bean.setName(permission.getName());
            bean.setPosition(permission.getPosition());
            bean.setType(permission.getType());
            bean.setLink(permission.getLink());
            bean.setIcon(permission.getIcon());
            bean.setParentId(permission.getParentId());
            bean.setState(permission.getState()); // opened
        } else {
            String cId = bean.getId();
            if (permission.getParentId() != null && cId.equals(permission.getParentId())) {
                PermissionBean spb = new PermissionBean();
                spb.setId(permission.getId());
                spb.setName(permission.getName());
                spb.setPosition(permission.getPosition());
                spb.setType(permission.getType());
                spb.setLink(permission.getLink());
                spb.setIcon(permission.getIcon());
                spb.setParentId(permission.getParentId());
                spb.setState(permission.getState()); // closed
                if (bean.getChildren() == null) {
                    bean.setChildren(new ArrayList<>());
                }
                if (!TYPE_OPERATE.equals(spb.getType())) {
                    bean.getChildren().add(spb);
                }
            } else {
                for (PermissionBean children : bean.getChildren()) {
                    convertToMenu(children, permission);
                }
            }
        }
        return bean;
    }

}
