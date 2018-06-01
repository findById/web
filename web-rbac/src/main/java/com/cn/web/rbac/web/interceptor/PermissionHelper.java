package com.cn.web.rbac.web.interceptor;

import org.springframework.web.method.HandlerMethod;

import java.lang.annotation.Annotation;

public class PermissionHelper {
    public static <A extends Annotation> A findMethodOrDeclaringClassAnn(Object handler, Class<A> clazz) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;
            Annotation annotation = hm.getMethod().getAnnotation(clazz);
            if (annotation == null) {
                annotation = hm.getMethod().getDeclaringClass().getAnnotation(clazz);
            }
            if (annotation == null) {
                return null;
            }
            return (A) annotation;
        }
        return null;
    }
}
