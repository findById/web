package com.cn.web.rbac.web.converter;

import com.cn.web.rbac.domain.Permission;
import com.cn.web.rbac.web.vo.PermissionBean;

import java.util.ArrayList;
import java.util.List;

public class PermissionConverter {

    public static PermissionBean convertToMenu(List<Permission> permissionList) {
        PermissionBean bean = null;
        for (Permission permission : permissionList) {
            bean = convert(bean, permission);
        }
        return bean;
    }

    private static PermissionBean convert(PermissionBean bean, Permission permission) {
        if (bean == null) { // root node
            bean = new PermissionBean();
            bean.setId(permission.getId());
            bean.setName(permission.getName());
            bean.setLink(permission.getLink());
            bean.setState("opened");
        } else {
            String cId = bean.getId();
            if (permission.getParentId() != null && cId.equals(permission.getParentId())) {
                PermissionBean spb = new PermissionBean();
                spb.setId(permission.getId());
                spb.setName(permission.getName());
                spb.setType(permission.getType());
                if ("permission".equals(spb.getType())) {
                    spb.setType("btn");
                }
                spb.setMethod(permission.getMethod());
                spb.setLink(permission.getLink());
                spb.setState("closed");
                if (bean.getChildren() == null) {
                    bean.setChildren(new ArrayList<>());
                }
                bean.getChildren().add(spb);
            } else {
                for (PermissionBean children : bean.getChildren()) {
                    convert(children, permission);
                }
            }
        }
        return bean;
    }

}
