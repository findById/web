package org.cn.web.admin.web.controller;

import com.cn.web.core.platform.exception.HandlerException;
import com.cn.web.core.platform.web.DefaultController;
import com.cn.web.core.platform.web.ResponseBuilder;
import com.cn.web.rbac.domain.Dict;
import com.cn.web.rbac.web.handler.DictHandler;
import com.cn.web.rbac.web.interceptor.PermissionRequired;
import com.cn.web.rbac.web.request.DictReq;
import com.cn.web.rbac.web.vo.DictBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "dict")
public class DictController extends DefaultController {

    @Autowired
    DictHandler dictHandler;

    @PermissionRequired(value = "sys:dict:save")
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String save(DictReq req) {
        ResponseBuilder.Builder builder = ResponseBuilder.newBuilder();
        try {

            DictBean bean = dictHandler.save(req);

            builder.result(bean);
            builder.message("success");
            builder.statusCode(200);
        } catch (HandlerException e) {
            builder.message(e.getMessage());
            builder.statusCode(e.getStatusCode());
        }
        return builder.buildJSONString();
    }

    @PermissionRequired(value = "sys:dict:update")
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(Dict dict) {
        ResponseBuilder.Builder builder = ResponseBuilder.newBuilder();
        try {

            dictHandler.update(dict);

            builder.message("success");
            builder.statusCode(200);
        } catch (HandlerException e) {
            builder.message(e.getMessage());
            builder.statusCode(e.getStatusCode());
        }
        return builder.buildJSONString();
    }

    @PermissionRequired(value = "sys:dict:delete")
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public String delete(String id) {
        ResponseBuilder.Builder builder = ResponseBuilder.newBuilder();
        try {

            dictHandler.delete(id);

            builder.message("success");
            builder.statusCode(200);
        } catch (HandlerException e) {
            builder.message(e.getMessage());
            builder.statusCode(e.getStatusCode());
        }
        return builder.buildJSONString();
    }

    @PermissionRequired(value = "sys:dict:view")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list() {
        ResponseBuilder.Builder builder = ResponseBuilder.newBuilder();
        try {

            List<Dict> result = dictHandler.list();

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
