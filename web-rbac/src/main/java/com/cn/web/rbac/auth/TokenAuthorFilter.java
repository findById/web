package com.cn.web.rbac.auth;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@WebFilter
public class TokenAuthorFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String token = req.getHeader("token");
        System.out.println("uri: " + req.getRequestURI() + ", token: " + token);

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
