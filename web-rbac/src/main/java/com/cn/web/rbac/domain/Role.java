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

    public Role() {
    }

    public Role(String name, String code, String description) {
        super(description);
        this.name = name;
        this.code = code;
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
}
