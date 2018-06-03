package com.cn.web.rbac.web.controller;

import com.cn.web.core.platform.web.DefaultController;
import com.cn.web.core.platform.web.ResponseBuilder;
import com.cn.web.rbac.domain.Role;
import com.cn.web.rbac.service.RoleService;
import com.cn.web.rbac.util.PageUtils;
import com.cn.web.rbac.web.interceptor.PermissionRequired;
import com.cn.web.rbac.web.request.RoleReq;
import com.cn.web.rbac.web.vo.RoleBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "role")
public class RoleController extends DefaultController {

    @Autowired
    RoleService roleService;

    @PermissionRequired("sys:role:save")
    @RequestMapping(value = "save", method = {RequestMethod.POST})
    public String save(@RequestBody RoleReq req) {
        ResponseBuilder.Builder builder = ResponseBuilder.newBuilder();

        if (req == null) {
            builder.statusCode(201);
            builder.message("Request body must not be null.");
            return builder.buildJSONString();
        }
        if (req.getName() == null || req.getName().isEmpty()) {
            builder.statusCode(201);
            builder.message("'name' must not be null.");
            return builder.buildJSONString();
        }

        Role role = new Role();
        role.setId(req.getId());
        role.setName(req.getName());
        role.setCode(req.getCode());
        role.setDescription(req.getDescription());

        roleService.save(role);

        RoleBean bean = new RoleBean();
        BeanUtils.copyProperties(role, bean);

        builder.statusCode(200);
        builder.message("success");
        builder.result(bean);
        return builder.buildJSONString();
    }

    @PermissionRequired("sys:role:update")
    @RequestMapping(value = "update", method = {RequestMethod.POST})
    public String update(@RequestBody RoleReq req) {
        ResponseBuilder.Builder builder = ResponseBuilder.newBuilder();

        if (req == null || req.getId() == null) {
            builder.statusCode(201);
            builder.message("role not exists");
            return builder.buildJSONString();
        }

        Role role = roleService.get(req.getId());
        if (role == null) {
            builder.statusCode(201);
            builder.message("role not exists");
            return builder.buildJSONString();
        }
        if (req.getName() != null && !req.getName().isEmpty()) {
            role.setName(req.getName());
        }
        if (req.getCode() != null && !req.getCode().isEmpty()) {
            role.setCode(req.getCode());
        }
        if (req.getDescription() != null && !req.getDescription().isEmpty()) {
            role.setDescription(req.getDescription());
        }

        roleService.update(role);

        builder.statusCode(200);
        builder.message("success");
        return builder.buildJSONString();
    }

    @PermissionRequired("sys:role:delete")
    @RequestMapping(value = "delete")
    public String delete(String id) {
        ResponseBuilder.Builder builder = ResponseBuilder.newBuilder();

        roleService.delete(id);

        builder.statusCode(200);
        builder.message("ok");
        return builder.buildJSONString();
    }

    @PermissionRequired("sys:role:view")
    @RequestMapping(value = "list")
    public String list(String page, String size) {
        ResponseBuilder.Builder builder = ResponseBuilder.newBuilder();
        int[] temp = PageUtils.of(page, size);

        Page<Role> list = roleService.list(temp[0], temp[1]);

        List<RoleBean> beanList = new ArrayList<>();
        if (list.hasContent()) {
            for (Role role : list.getContent()) {
                RoleBean bean = new RoleBean();
                bean.setId(role.getId());
                bean.setName(role.getName());
                bean.setCode(role.getCode());
                bean.setDescription(role.getDescription());
                beanList.add(bean);
            }
        }

        HashMap<String, Object> result = new HashMap<>();
        result.put("page", temp[0]);
        result.put("total", list.getTotalElements());
        result.put("list", beanList);

        builder.statusCode(200);
        builder.message("success");
        builder.result(result);

        return builder.buildJSONString();
    }
}
