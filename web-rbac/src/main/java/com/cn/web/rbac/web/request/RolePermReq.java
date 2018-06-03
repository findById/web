package com.cn.web.rbac.web.request;

import java.io.Serializable;
import java.util.List;

public class RolePermReq implements Serializable {
    private String roleId;
    private List<String> permIds;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public List<String> getPermIds() {
        return permIds;
    }

    public void setPermIds(List<String> permIds) {
        this.permIds = permIds;
    }
}
