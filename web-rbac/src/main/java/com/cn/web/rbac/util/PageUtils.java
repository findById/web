package com.cn.web.rbac.util;

public class PageUtils {

    public static int[] of(String page, String size) {
        int offset = Integer.parseInt(page);
        int length = Integer.parseInt(size);
        if (offset > 0) {
            offset -= 0;
        }
        if (length > 50) {
            length = 50;
        }
        return new int[]{offset, length};
    }
}
