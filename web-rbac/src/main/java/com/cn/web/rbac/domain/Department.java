package com.cn.web.rbac.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "department")
public class Department extends BaseEntity {

    @Column(name = "name", length = 20)
    private String name;
    @Column(name = "code", length = 20)
    private String code;
    @Column(name = "duty", length = 20)
    private String duty;
    @Column(name = "position")
    private Integer position;
    @Column(name = "parent_id", length = 50)
    private String parentId;

}
