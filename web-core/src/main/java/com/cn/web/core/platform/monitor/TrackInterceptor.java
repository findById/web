package com.cn.web.core.platform.monitor;

import com.cn.web.core.util.IPUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;

public class TrackInterceptor implements HandlerInterceptor {

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        StringBuilder sb = new StringBuilder();
        // access time
        sb.append("[").append(format.format(System.currentTimeMillis())).append("]");
        // address
        sb.append("[").append(IPUtils.getRemoteAddr(request)).append("]");
        // method
        sb.append('[');
        if ((handler instanceof HandlerMethod)) {
            HandlerMethod method = (HandlerMethod) handler;
            sb.append(method.getBeanType().getName()).append(".").append(method.getMethod().getName()).append("()");
        }
        sb.append(']');
        // url
        sb.append("[").append(request.getScheme()).append("://")
                .append(request.getServerName()).append(":")
                .append(request.getServerPort())
                .append(request.getServletPath())
                .append(getQueryString(request)).append("]");
        System.out.println(sb.toString());
        return true;
    }

    public String getQueryString(HttpServletRequest request) {
        String query = request.getQueryString();
        if (query != null && !query.isEmpty()) {
            return "?" + query;
        }
        return "";
    }
}
