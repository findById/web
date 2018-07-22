package org.cn.web.admin.web.controller;

import com.cn.web.core.platform.exception.HandlerException;
import com.cn.web.core.platform.web.DefaultController;
import com.cn.web.core.platform.web.ResponseBuilder;
import com.cn.web.rbac.web.handler.RoleHandler;
import com.cn.web.rbac.web.interceptor.PermissionRequired;
import com.cn.web.rbac.web.request.RoleReq;
import com.cn.web.rbac.web.vo.RoleBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import java.util.HashMap;

@RestController
@RequestMapping(value = "role")
public class RoleController extends DefaultController {

    @Autowired
    RoleHandler roleHandler;

    @PermissionRequired("sys:role:save")
    @RequestMapping(value = "save", method = {RequestMethod.POST})
    public String save(@RequestBody RoleReq req) {
        ResponseBuilder builder = ResponseBuilder.newBuilder();
        try {

            RoleBean bean = roleHandler.save(req);

            builder.result(bean);
            builder.message("success");
            builder.statusCode(200);
        } catch (HandlerException e) {
            builder.message(e.getMessage());
            builder.statusCode(e.getStatusCode());
        }
        return builder.buildJSONString();
    }

    @PermissionRequired("sys:role:update")
    @RequestMapping(value = "update", method = {RequestMethod.POST})
    public String update(@RequestBody RoleReq req) {
        ResponseBuilder builder = ResponseBuilder.newBuilder();
        try {

            roleHandler.update(req);

            builder.message("success");
            builder.statusCode(200);
        } catch (HandlerException e) {
            builder.message(e.getMessage());
            builder.statusCode(e.getStatusCode());
        }
        return builder.buildJSONString();
    }

    @PermissionRequired("sys:role:delete")
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public String delete(String ids) {
        ResponseBuilder builder = ResponseBuilder.newBuilder();
        try {

            String[] array;
            if (ids.contains(",")) {
                array = ids.split(",");
            } else {
                array = new String[]{ids};
            }

            roleHandler.deleteByLogic(array);

            builder.message("success");
            builder.statusCode(200);
        } catch (HandlerException e) {
            builder.message(e.getMessage());
            builder.statusCode(e.getStatusCode());
        }
        return builder.buildJSONString();
    }

    @PermissionRequired("sys:role:view")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(@RequestParam(name = "page", defaultValue = "1") int page,
                       @RequestParam(name = "size", defaultValue = "20") @Max(50) int size,
                       @RequestParam(name = "keywords", required = false) String keywords) {
        ResponseBuilder builder = ResponseBuilder.newBuilder();
        try {

            HashMap<String, Object> result;
            if (StringUtils.isEmpty(keywords)) {
                result = roleHandler.search(keywords, page, size);
            } else {
                result = roleHandler.list(page, size);
            }

            builder.result(result);
            builder.message("success");
            builder.statusCode(200);
        } catch (HandlerException e) {
            builder.message(e.getMessage());
            builder.statusCode(e.getStatusCode());
        }
        return builder.buildJSONString();
    }
}
