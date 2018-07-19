package com.cn.web.rbac.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "user_role")
public class UserRole extends BaseEntity {

    @Column(name = "user_id", length = 50)
    private String userId;
    @Column(name = "role_id", length = 50)
    private String roleId;

    public UserRole() {
    }

    public UserRole(String userId, String roleId, String description) {
        super(description);
        this.userId = userId;
        this.roleId = roleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
