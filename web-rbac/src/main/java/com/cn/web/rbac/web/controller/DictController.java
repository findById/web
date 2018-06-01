package com.cn.web.rbac.web.controller;

import com.alibaba.fastjson.JSON;
import com.cn.web.rbac.domain.Dict;
import com.cn.web.rbac.service.DictService;
import com.cn.web.rbac.web.interceptor.PermissionRequired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "dict")
public class DictController {

    @Autowired
    DictService dictService;

    @PermissionRequired(value = "sys:dict:save")
    @RequestMapping(value = "save")
    public String save(Dict dict) {
        dictService.save(dict);
        return JSON.toJSONString(dict);
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
