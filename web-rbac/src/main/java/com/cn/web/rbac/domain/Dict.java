package com.cn.web.rbac.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "dict")
public class Dict extends BaseEntity {

    @Column(name = "label", length = 50)
    private String label;
    @Column(name = "value", length = 200)
    private String value;
    @Column(name = "type", length = 50)
    private String type;
    @Column(name = "position")
    private Integer position;
    @Column(name = "remark", length = 200)
    private String remark;
    @Column(name = "parent_id", length = 50)
    private String parentId;

    public Dict() {
    }

    public Dict(String label, String value, String type, Integer position, String remark, String parentId, String description) {
        super(description);
        this.label = label;
        this.value = value;
        this.type = type;
        this.position = position;
        this.remark = remark;
        this.parentId = parentId;
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
}
