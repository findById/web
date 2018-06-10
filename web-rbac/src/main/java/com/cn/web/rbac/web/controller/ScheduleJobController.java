package com.cn.web.rbac.web.controller;

import com.cn.web.core.platform.web.DefaultController;
import com.cn.web.core.platform.web.ResponseBuilder;
import com.cn.web.rbac.domain.ScheduleJob;
import com.cn.web.rbac.service.ScheduleJobService;
import com.cn.web.rbac.util.PageUtils;
import com.cn.web.rbac.web.request.ScheduleJobReq;
import com.cn.web.rbac.web.response.ScheduleJobResp;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "scheduleJob")
public class ScheduleJobController extends DefaultController {

    @Resource
    ScheduleJobService scheduleJobService;

    // @PermissionRequired(value = "sys:task:view")
    @RequestMapping(value = "search", method = {RequestMethod.POST})
    public String search(String keyword, String page, String size) {
        ResponseBuilder.Builder builder = ResponseBuilder.newBuilder();

        int[] temp = PageUtils.of(page, size);

        Page<ScheduleJob> list = scheduleJobService.search(keyword, PageRequest.of(temp[0], temp[1]));

        List<ScheduleJobResp> beanList = new ArrayList<>();
        if (list.hasContent()) {
            for (ScheduleJob scheduleJob : list.getContent()) {
                ScheduleJobResp item = new ScheduleJobResp();
                BeanUtils.copyProperties(scheduleJob, item);
                beanList.add(item);
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("page", temp[0]);
        result.put("total", list.getTotalElements());
        result.put("list", beanList);

        builder.statusCode(200);
        builder.message("success");
        builder.result(result);
        return builder.buildJSONString();
    }

    // @PermissionRequired(value = "sys:task:save")
    @RequestMapping(value = "save", method = {RequestMethod.POST})
    public String save(@RequestBody ScheduleJobReq req) {
        ResponseBuilder.Builder builder = ResponseBuilder.newBuilder();

        if (req == null) {
            builder.statusCode(201);
            builder.message("Request body must not be null.");
            return builder.buildJSONString();
        }

        ScheduleJob scheduleJob = new ScheduleJob();
        // TODO copy req

        scheduleJobService.save(scheduleJob);

        ScheduleJobResp resp = new ScheduleJobResp();
        BeanUtils.copyProperties(scheduleJob, resp);

        builder.statusCode(200);
        builder.message("success");
        builder.result(resp);
        return builder.buildJSONString();
    }

    // @PermissionRequired(value = "sys:task:update")
    @RequestMapping(value = "update", method = {RequestMethod.POST})
    public String update(@RequestBody ScheduleJobReq req) {
        ResponseBuilder.Builder builder = ResponseBuilder.newBuilder();

        if (req == null || req.getId() == null) {
            builder.statusCode(201);
            builder.message("ScheduleJob not exists");
            return builder.buildJSONString();
        }

        ScheduleJob scheduleJob = scheduleJobService.get(req.getId());
        if (scheduleJob == null) {
            builder.statusCode(201);
            builder.message("ScheduleJob not exists");
            return builder.buildJSONString();
        }
        // TODO copy value

        scheduleJobService.update(scheduleJob);

        builder.statusCode(200);
        builder.message("success");
        return builder.buildJSONString();
    }

    // @PermissionRequired(value = "sys:task:delete")
    @RequestMapping(value = "delete")
    public String delete(String ids) {
        ResponseBuilder.Builder builder = ResponseBuilder.newBuilder();

        scheduleJobService.delete(ids);

        builder.statusCode(200);
        builder.message("success");
        return builder.buildJSONString();
    }

    // @PermissionRequired(value = "sys:task:view")
    @RequestMapping(value = "list")
    public String list(String page, String size) {
        ResponseBuilder.Builder builder = ResponseBuilder.newBuilder();

        int[] temp = PageUtils.of(page, size);

        Page<ScheduleJob> list = scheduleJobService.list(temp[0], temp[1]);

        List<ScheduleJobResp> beanList = new ArrayList<>();
        if (list.hasContent()) {
            for (ScheduleJob scheduleJob : list.getContent()) {
                ScheduleJobResp item = new ScheduleJobResp();
                BeanUtils.copyProperties(scheduleJob, item);
                beanList.add(item);
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("page", temp[0]);
        result.put("total", list.getTotalElements());
        result.put("list", beanList);

        builder.statusCode(200);
        builder.message("success");
        builder.result(result);
        return builder.buildJSONString();
    }

    // @PermissionRequired(value = "sys:task:view")
    @RequestMapping(value = "view")
    public String view() {
        return "unimplemented";
    }
}
