package com.cn.web.rbac.web.interceptor;

import com.alibaba.fastjson.JSON;
import com.cn.web.rbac.domain.Permission;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.List;

public class SessionContext {
    public static final String ACCESS_PERMISSION_LIST = "access.permission";
    public static final String ACCESS_SESSION = "access.session";

    public static String getSessionId() {
        return RequestContextHolder.getRequestAttributes().getSessionId();
    }

    public static HttpSession getSession() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
    }

    public static Object getAttribute(String key) {
        return getSession().getAttribute(key);
    }

    public static void setAttribute(String key, Object value) {
        getSession().setAttribute(key, value);
    }

    public static void clearSession() {
        try {
            Enumeration enumeration = getSession().getAttributeNames();
            while (enumeration.hasMoreElements()) {
                getSession().setAttribute(String.valueOf(enumeration.nextElement()), "");
            }
        } catch (Throwable ignored) {
        }
    }

    public static void setPermissionList(List<Permission> list) {
        setAttribute(ACCESS_PERMISSION_LIST, list == null ? null : JSON.toJSONString(list));
    }

    public static List<Permission> getPermissionList() {
        Object obj = getAttribute(ACCESS_PERMISSION_LIST);
        if (obj != null) {
            return JSON.parseArray(obj.toString(), Permission.class);
        }
        return null;
    }
}
