package com.cn.web.rbac.web.controller;

import com.alibaba.fastjson.JSON;
import com.cn.web.core.platform.web.DefaultController;
import com.cn.web.core.platform.web.ResponseBuilder;
import com.cn.web.rbac.domain.Dict;
import com.cn.web.rbac.service.DictService;
import com.cn.web.rbac.web.interceptor.PermissionRequired;
import com.cn.web.rbac.web.request.DictReq;
import com.cn.web.rbac.web.vo.DictBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "dict")
public class DictController extends DefaultController {

    @Autowired
    DictService dictService;

    @PermissionRequired(value = "sys:dict:save")
    @RequestMapping(value = "save")
    public String save(DictReq req) {
        ResponseBuilder.Builder builder = ResponseBuilder.newBuilder();
        if (req == null) {
            builder.statusCode(201);
            builder.message("Request body must not be null.");
            return builder.buildJSONString();
        }
        Dict dict = new Dict();
        dict.setLabel(req.getLabel());
        dict.setValue(req.getValue());
        dict.setType(req.getType());
        dict.setPosition(req.getPosition());
        dict.setRemark(req.getRemark());

        dictService.save(dict);

        DictBean bean = new DictBean();
        BeanUtils.copyProperties(dict, bean);

        builder.statusCode(200);
        builder.message("message");
        builder.result(bean);
        return builder.buildJSONString();
    }

    @PermissionRequired(value = "sys:dict:update")
    @RequestMapping(value = "update")
    public String update(Dict dict) {
        dictService.update(dict);
        return "unimplemented";
    }

    @PermissionRequired(value = "sys:dict:delete")
    @RequestMapping(value = "delete")
    public String delete(String id) {
        dictService.delete(id);
        return "success";
    }

    @PermissionRequired(value = "sys:dict:view")
    @RequestMapping(value = "list")
    public String list() {
        List<Dict> list = dictService.list();
        return JSON.toJSONString(list);
    }
}
