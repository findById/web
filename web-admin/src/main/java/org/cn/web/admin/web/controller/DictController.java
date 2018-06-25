package org.cn.web.admin.web.controller;

import com.cn.web.core.platform.web.DefaultController;
import com.cn.web.rbac.domain.Dict;
import com.cn.web.rbac.web.handler.DictHandler;
import com.cn.web.rbac.web.interceptor.PermissionRequired;
import com.cn.web.rbac.web.request.DictReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "dict")
public class DictController extends DefaultController {

    @Autowired
    DictHandler dictHandler;

    @PermissionRequired(value = "sys:dict:save")
    @RequestMapping(value = "save")
    public String save(DictReq req) {
        return dictHandler.save(req);
    }

    @PermissionRequired(value = "sys:dict:update")
    @RequestMapping(value = "update")
    public String update(Dict dict) {
        return dictHandler.update(dict);
    }

    @PermissionRequired(value = "sys:dict:delete")
    @RequestMapping(value = "delete")
    public String delete(String id) {
        return dictHandler.delete(id);
    }

    @PermissionRequired(value = "sys:dict:view")
    @RequestMapping(value = "list")
    public String list() {
        return dictHandler.list();
    }
}
