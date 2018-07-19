package com.cn.web.rbac.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "role_permission")
public class RolePermission extends BaseEntity {

    @Column(name = "role_id", length = 50)
    private String roleId;
    @Column(name = "permission_id", length = 50)
    private String permissionId;

    public RolePermission() {
    }

    public RolePermission(String roleId, String permissionId, String description) {
        super(description);
        this.roleId = roleId;
        this.permissionId = permissionId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }
}
