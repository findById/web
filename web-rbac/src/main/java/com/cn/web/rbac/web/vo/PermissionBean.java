package com.cn.web.rbac.web.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PermissionBean implements Serializable {

    private String id;
    private String name;
    private String type;
    private Integer position;
    private String link;
    private String permCode;
    private String method;
    private String icon;
    private String parentId;
    private String state;
    private List<PermissionBean> children = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<PermissionBean> getChildren() {
        return children;
    }

    public void setChildren(List<PermissionBean> children) {
        this.children = children;
    }
}
