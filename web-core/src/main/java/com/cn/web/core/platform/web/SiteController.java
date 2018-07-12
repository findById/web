package com.cn.web.core.platform.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.beans.PropertyEditorSupport;

public class SiteController extends DefaultController {

    private static final int DEFAULT_PAGE_NO = 1;
    private static final int DEFAULT_PAGE_SIZE = 20;
    private static final int MAX_PAGE_SIZE = 50;

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;

    @Autowired
    protected HttpSession session;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // Cross Site Scripting (XSS)
        binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                // JavaScriptUtils.javaScriptEscape(text);
                // HtmlUtils.htmlEscape(text);
                setValue(text == null ? null : HtmlUtils.htmlEscape(text));
                // System.out.println("in: " + text + ", out: " + getValue());
            }
        });
    }

    public PageRequest getPageRequest() {
        int pageNo = 0;
        int pageSize = DEFAULT_PAGE_SIZE;
        String page = request.getParameter("page");
        String size = request.getParameter("size");
        String orderBy = request.getParameter("sort");
        String order = request.getParameter("order");
        if (page != null && !page.isEmpty()) {
            try {
                pageNo = Integer.parseInt(page);
            } catch (Throwable ignored) {
            }
        }
        if (size != null && !size.isEmpty()) {
            try {
                pageSize = Integer.parseInt(size);
            } catch (Throwable e) {
                pageSize = DEFAULT_PAGE_SIZE;
            }
        }
        if (pageNo <= 0) {
            pageNo = DEFAULT_PAGE_NO;
        }
        if (pageSize > MAX_PAGE_SIZE) {
            pageSize = MAX_PAGE_SIZE;
        }
        if (orderBy == null || orderBy.isEmpty()) {
            orderBy = "id";
        }
        if (order == null || order.isEmpty()) {
            order = "ASC";
        }
        if (Sort.Direction.ASC.toString().toUpperCase().equals(order)) {
            return PageRequest.of(pageNo, pageSize, Sort.Direction.ASC, orderBy.split(","));
        } else {
            return PageRequest.of(pageNo, pageSize, Sort.Direction.DESC, orderBy.split(","));
        }
    }
}
