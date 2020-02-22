package com.cn.web.rbac.web.interceptor;

public enum Logical {
    AND(0),
    OR(1),
    OWNER(2);

    int value;

    Logical(int value) {
        this.value = value;
    }
}
