package com.cn.web.rbac.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "permission")
public class Permission extends BaseEntity {

    @Column(name = "name", length = 20)
    private String name;
    @Column(name = "type", length = 20)
    private String type;
    @Column(name = "position")
    private Integer position;
    @Column(name = "link", length = 100)
    private String link;
    @Column(name = "perm_code", length = 100)
    private String permCode;
    @Column(name = "method", length = 5)
    private String method;
    @Column(name = "state", length = 1)
    private String state;
    @Column(name = "parent_id", length = 50)
    private String parentId;

    public Permission() {
    }

    public Permission(String name, String type, Integer position, String link, String permCode, String parentId, String description) {
        super(description);
        this.name = name;
        this.type = type;
        this.position = position;
        this.link = link;
        this.permCode = permCode;
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPermCode() {
        return permCode;
    }

    public void setPermCode(String permCode) {
        this.permCode = permCode;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
