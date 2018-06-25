package com.cn.web.rbac.web.handler;

import com.cn.web.core.platform.web.ResponseBuilder;
import com.cn.web.rbac.domain.ScheduleJob;
import com.cn.web.rbac.service.ScheduleJobService;
import com.cn.web.rbac.web.request.ScheduleJobReq;
import com.cn.web.rbac.web.response.ScheduleJobResp;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("scheduleJobHandler")
public class ScheduleJobHandler {

    @Autowired
    ScheduleJobService scheduleJobService;

    public String search(String keyword, int page, /* @Max(20) */ int size) {
        ResponseBuilder.Builder builder = ResponseBuilder.newBuilder();
        if (page < 0) {
            page = 0;
        }
        if (size > 20) {
            size = 20;
        }

        Page<ScheduleJob> list = scheduleJobService.search(keyword, PageRequest.of(page - 1, size));

        List<ScheduleJobResp> beanList = new ArrayList<>();
        if (list.hasContent()) {
            for (ScheduleJob scheduleJob : list.getContent()) {
                ScheduleJobResp item = new ScheduleJobResp();
                BeanUtils.copyProperties(scheduleJob, item);
                beanList.add(item);
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("page", page);
        result.put("total", list.getTotalElements());
        result.put("list", beanList);

        builder.statusCode(200);
        builder.message("success");
        builder.result(result);
        return builder.buildJSONString();
    }

    public String save(ScheduleJobReq req) {
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

    public String update(ScheduleJobReq req) {
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

    public String delete(String ids) {
        ResponseBuilder.Builder builder = ResponseBuilder.newBuilder();

        scheduleJobService.delete(ids);

        builder.statusCode(200);
        builder.message("success");
        return builder.buildJSONString();
    }

    public String list(int page, /* @Max(20) */ int size) {
        ResponseBuilder.Builder builder = ResponseBuilder.newBuilder();
        if (page < 0) {
            page = 0;
        }
        if (size > 20) {
            size = 20;
        }

        Page<ScheduleJob> list = scheduleJobService.list(page - 1, size);

        List<ScheduleJobResp> beanList = new ArrayList<>();
        if (list.hasContent()) {
            for (ScheduleJob scheduleJob : list.getContent()) {
                ScheduleJobResp item = new ScheduleJobResp();
                BeanUtils.copyProperties(scheduleJob, item);
                beanList.add(item);
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("page", page);
        result.put("total", list.getTotalElements());
        result.put("list", beanList);

        builder.statusCode(200);
        builder.message("success");
        builder.result(result);
        return builder.buildJSONString();
    }

    public String start(String id) {
        ResponseBuilder.Builder builder = ResponseBuilder.newBuilder();

        builder.statusCode(200);
        builder.message("success");
        return builder.buildJSONString();
    }

    public String pause(String id) {
        ResponseBuilder.Builder builder = ResponseBuilder.newBuilder();

        builder.statusCode(200);
        builder.message("success");
        return builder.buildJSONString();
    }

    public String resume(String id) {
        ResponseBuilder.Builder builder = ResponseBuilder.newBuilder();

        builder.statusCode(200);
        builder.message("success");
        return builder.buildJSONString();
    }
}
