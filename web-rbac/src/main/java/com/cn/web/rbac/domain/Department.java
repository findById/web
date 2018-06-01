package com.cn.web.rbac.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "department")
public class Department extends BaseEntity {

    @Column(name = "name", length = 20)
    private String name;
    @Column(name = "position")
    private Integer position;
    @Column(name = "parent_id", length = 50)
    private String parentId;
    @Column(name = "parentName", length = 20)
    private String parentName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
}
