package com.cn.web.rbac.web.request;

import java.io.Serializable;

public class DepartmentReq implements Serializable {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
