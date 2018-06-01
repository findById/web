package com.cn.web.core.platform.monitor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;

public class TrackInterceptor implements HandlerInterceptor {

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS Z");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        StringBuffer sb = new StringBuffer();
        sb.append("[").append(format.format(System.currentTimeMillis())).append("]");
        sb.append("[").append(getRemoteAddr(request)).append("]");
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

    public String getRemoteAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Http_X_Forwarded_For");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Http-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.contains(",")) {
            ip = ip.split(",")[0];
        }
        return ip;
    }

}
