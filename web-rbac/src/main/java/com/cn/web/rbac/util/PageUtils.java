package com.cn.web.rbac.util;

import org.springframework.data.domain.PageRequest;

public class PageUtils {

    public static PageRequest of(int page, int size) {
        return PageRequest.of(page, size);
    }
}
