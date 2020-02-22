package com.cn.web.rbac.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "permission")
public class Permission extends BaseEntity {
    public static final String TYPE_MENU = "menu";
    public static final String TYPE_PERMISSION = "permission";

    public static final short VISIBLE_GONE = 0;
    public static final short VISIBLE_VISIBLE = 1;

    @Column(name = "name", length = 20)
    private String name;
    @Column(name = "type", length = 20)
    private String type = TYPE_MENU;
    @Column(name = "position")
    private Integer position;
    @Column(name = "link", length = 100)
    private String link;
    @Column(name = "perm_code", length = 100)
    private String permCode;
    @Column(name = "method", length = 5)
    private String method;
    @Column(name = "icon", length = 50)
    private String icon;
    @Column(name = "visible", length = 2)
    private Short visible = VISIBLE_VISIBLE;
    @Column(name = "parent_id", length = 50)
    private String parentId;

    public Permission() {
    }

    public Permission(String name, String type, Integer position, String link, String permCode, String parentId, String description) {
        this(name, type, position, link, permCode, VISIBLE_VISIBLE, parentId, description);
    }

    public Permission(String name, String type, Integer position, String link, String permCode, Short visible, String parentId, String description) {
        this(name, type, position, link, permCode, null, visible, parentId, description);
    }

    public Permission(String name, String type, Integer position, String link, String permCode, String icon, Short visible, String parentId, String description) {
        super(description);
        this.name = name;
        this.type = type;
        this.position = position;
        this.link = link;
        this.permCode = permCode;
        this.icon = icon;
        this.visible = visible;
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Short getVisible() {
        return visible;
    }

    public void setVisible(Short visible) {
        this.visible = visible;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
