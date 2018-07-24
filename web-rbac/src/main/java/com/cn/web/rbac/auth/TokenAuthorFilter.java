package com.cn.web.rbac.auth;

import com.cn.web.rbac.util.IPUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;

@WebFilter
public class TokenAuthorFilter implements Filter {
    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        StringBuilder sb = new StringBuilder();
        // access time
        sb.append('[').append(format.format(System.currentTimeMillis())).append(']');
        // address
        sb.append("[").append(IPUtils.getRemoteAddr(req)).append("]");
        // url
        sb.append("[").append(req.getScheme()).append("://")
                .append(req.getServerName()).append(":")
                .append(req.getServerPort())
                .append(req.getServletPath());

        String query = req.getQueryString();
        if (query != null && !query.isEmpty()) {
            sb.append("?").append(query);
        }
        sb.append("]");

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
