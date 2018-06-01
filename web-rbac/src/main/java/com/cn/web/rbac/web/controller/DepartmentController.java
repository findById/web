package com.cn.web.rbac.web.controller;

import com.alibaba.fastjson.JSON;
import com.cn.web.core.platform.web.ResponseBuilder;
import com.cn.web.rbac.domain.Department;
import com.cn.web.rbac.service.DepartmentService;
import com.cn.web.rbac.web.request.DepartmentReq;
import com.cn.web.rbac.web.response.DepartmentResp;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "department")
public class DepartmentController {

    @Resource
    DepartmentService departmentService;

    // @PermissionRequired(value = "sys:department:save")
    @RequestMapping(value = "save", method = {RequestMethod.POST})
    public String save(@RequestBody DepartmentReq req) {
        ResponseBuilder.Builder builder = ResponseBuilder.newBuilder();

        if (req == null) {
            builder.statusCode(201);
            builder.message("Request body must not be null.");
            return builder.buildJSONString();
        }

        Department department = new Department();
        // TODO copy req

        departmentService.save(department);

        DepartmentResp resp = new DepartmentResp();
        BeanUtils.copyProperties(department, resp);

        builder.statusCode(200);
        builder.message("success");
        builder.result(resp);
        return builder.buildJSONString();
    }

    // @PermissionRequired(value = "sys:department:update")
    @RequestMapping(value = "update", method = {RequestMethod.POST})
    public String update(@RequestBody(required = false) String body) {
        ResponseBuilder.Builder builder = ResponseBuilder.newBuilder();

        if (body == null || body.length() <= 0) {
            builder.statusCode(201);
            builder.message("Request body must not be null.");
            return builder.buildJSONString();
        }
        DepartmentReq req = JSON.parseObject(body, DepartmentReq.class);
        if (req == null || req.getId() == null) {
            builder.statusCode(201);
            builder.message("Department not exists");
            return builder.buildJSONString();
        }

        Department department = departmentService.get(req.getId());
        if (department == null) {
            builder.statusCode(201);
            builder.message("Department not exists");
            return builder.buildJSONString();
        }
        // TODO copy value

        departmentService.update(department);

        builder.statusCode(200);
        builder.message("success");
        return builder.buildJSONString();
    }

    // @PermissionRequired(value = "sys:department:delete")
    @RequestMapping(value = "delete")
    public String delete(String ids) {
        ResponseBuilder.Builder builder = ResponseBuilder.newBuilder();

        departmentService.delete(ids);

        builder.statusCode(200);
        builder.message("success");
        return builder.buildJSONString();
    }

    // @PermissionRequired(value = "sys:department:view")
    @RequestMapping(value = "list")
    public String list(String page, String size) {
        ResponseBuilder.Builder builder = ResponseBuilder.newBuilder();

        // int[] temp = PageUtil.of(page, size);
        int[] temp = pageOf(page, size);

        Page<Department> list = departmentService.list(temp[0], temp[1]);

        List<DepartmentResp> beanList = new ArrayList<>();
        if (list.hasContent()) {
            for (Department department : list.getContent()) {
                DepartmentResp item = new DepartmentResp();
                BeanUtils.copyProperties(department, item);
                beanList.add(item);
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

    // @PermissionRequired(value = "sys:department:view")
    @RequestMapping(value = "view")
    public String view() {
        return "unimplemented";
    }

    private static int[] pageOf(String page, String size) {
        int offset, length;
        try {
            offset = Integer.parseInt(page);
        } catch (Throwable e) {
            offset = 0;
        }
        if (offset <= 0) {
            offset = 0;
        }
        try {
            length = Integer.parseInt(size);
        } catch (Throwable e) {
            length = 50;
        }
        if (length <= 0) {
            length = 10;
        }
        if (length > 50) {
            length = 50;
        }
        return new int[]{offset, length};
    }

}
