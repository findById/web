package com.cn.web.rbac.web.interceptor;

import com.alibaba.fastjson.JSON;
import com.cn.web.rbac.domain.Permission;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

public class PermissionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) { // ignore
            return true;
        }
        PermissionRequired ann = PermissionHelper.findMethodOrDeclaringClassAnn(handler, PermissionRequired.class);
        if (ann == null) { // ignore
            return true;
        }

        // login status authorized first.
        Object session = SessionContext.getAttribute(SessionContext.ACCESS_SESSION);
        if (session == null) {
            response.sendRedirect("/");
            return false;
        }

        Object permissionCache = SessionContext.getAttribute(SessionContext.ACCESS_PERMISSION_LIST);
        List<Permission> permissionList = JSON.parseArray(String.valueOf(permissionCache), Permission.class);
        if (!assertAuthorized(ann, permissionList)) {
            System.err.println("User is not permitted [" + Arrays.toString(ann.value()) + "]");
            response.sendRedirect("/");
            return false;
        }
        return true;
    }

    private boolean assertAuthorized(PermissionRequired ann, List<Permission> permissionList) {
        if (ann == null || ann.value() == null || ann.value().length <= 0) {
            System.err.println("Permission code is empty.");
            return true;
        }
        if (permissionList == null || permissionList.isEmpty()) {
            return false;
        }
        if (ann.logical() == Logical.AND) {
            for (String permCode : ann.value()) {
                if (permCode == null || permCode.isEmpty()) {
                    continue;
                }
                if (!isPermitted(permCode, permissionList)) {
                    return false;
                }
            }
            return true;
        } else if (ann.logical() == Logical.OR) {
            for (String permCode : ann.value()) {
                if (permCode == null || permCode.isEmpty()) {
                    continue;
                }
                if (isPermitted(permCode, permissionList)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isPermitted(String permCode, List<Permission> permissionList) {
        for (Permission perm : permissionList) {
            if (permCode.equals(perm.getPermCode())) {
                return true;
            }
        }
        return false;
    }
}
