package com.cn.web.rbac.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "role")
public class Role extends BaseEntity {

    @Column(name = "name", length = 20)
    private String name;
    @Column(name = "code", length = 50)
    private String code;
    @Column(name = "type", length = 20)
    private String type;
    @Column(name = "parent_id", length = 50)
    private String parentId;

    public Role() {
    }

    public Role(String name, String code, String type, String parentId, String description) {
        super(description);
        this.name = name;
        this.code = code;
        this.type = type;
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
