package com.cn.web.rbac.web.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DictBean implements Serializable {
    private String id;
    private short state;
    private String label;
    private String value;
    private String type;
    private Integer position;
    private String remark;
    private String parentId;
    private List<DictBean> children = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public short getState() {
        return state;
    }

    public void setState(short state) {
        this.state = state;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public List<DictBean> getChildren() {
        return children;
    }

    public void setChildren(List<DictBean> children) {
        this.children = children;
    }
}
