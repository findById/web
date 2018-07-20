package org.cn.web.admin.web.controller;

import com.cn.web.core.platform.exception.HandlerException;
import com.cn.web.core.platform.web.DefaultController;
import com.cn.web.core.platform.web.ResponseBuilder;
import com.cn.web.job.web.handler.ScheduleJobHandler;
import com.cn.web.job.web.request.ScheduleJobReq;
import com.cn.web.job.web.response.ScheduleJobResp;
import com.cn.web.job.web.vo.ScheduleJobBean;
import com.cn.web.rbac.web.interceptor.PermissionRequired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Map;

@RestController
@RequestMapping(value = "sys/scheduleJob")
public class ScheduleJobController extends DefaultController {

    @Autowired
    ScheduleJobHandler scheduleJobHandler;

    @PermissionRequired(value = "sys:scheduleJob:save")
    @RequestMapping(value = "save", method = {RequestMethod.POST})
    public String save(@RequestBody ScheduleJobReq req) {
        ResponseBuilder builder = ResponseBuilder.newBuilder();
        try {

            ScheduleJobResp bean = scheduleJobHandler.save(req);

            builder.result(bean);
            builder.message("success");
            builder.statusCode(200);
        } catch (HandlerException e) {
            builder.message(e.getMessage());
            builder.statusCode(e.getStatusCode());
        }
        return builder.buildJSONString();
    }

    @PermissionRequired(value = "sys:scheduleJob:update")
    @RequestMapping(value = "update", method = {RequestMethod.POST})
    public String update(@RequestBody ScheduleJobReq req) {
        ResponseBuilder builder = ResponseBuilder.newBuilder();
        try {

            scheduleJobHandler.update(req);

            builder.message("success");
            builder.statusCode(200);
        } catch (HandlerException e) {
            builder.message(e.getMessage());
            builder.statusCode(e.getStatusCode());
        }
        return builder.buildJSONString();
    }

    @PermissionRequired(value = "sys:scheduleJob:delete")
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public String delete(Long ids) {
        ResponseBuilder builder = ResponseBuilder.newBuilder();
        try {

            scheduleJobHandler.delete(ids);

            builder.message("success");
            builder.statusCode(200);
        } catch (HandlerException e) {
            builder.message(e.getMessage());
            builder.statusCode(e.getStatusCode());
        }
        return builder.buildJSONString();
    }

    @PermissionRequired(value = "sys:scheduleJob:view")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(@RequestParam(name = "page", defaultValue = "1") @Min(1) int page,
                       @RequestParam(name = "size", defaultValue = "20") @Max(50) int size,
                       @RequestParam(name = "keywords", required = false) String keywords) {
        ResponseBuilder builder = ResponseBuilder.newBuilder();
        try {
            Map<String, Object> result;
            if (!StringUtils.isEmpty(keywords)) {
                result = scheduleJobHandler.search(keywords, page, size);
            } else {
                result = scheduleJobHandler.list(page, size);
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

    @PermissionRequired(value = "sys:scheduleJob:start")
    @RequestMapping(value = "start", method = RequestMethod.POST)
    public String start(Long id) {
        ResponseBuilder builder = ResponseBuilder.newBuilder();
        try {

            scheduleJobHandler.start(new Long[]{id});

            builder.message("success");
            builder.statusCode(200);
        } catch (HandlerException e) {
            builder.message(e.getMessage());
            builder.statusCode(e.getStatusCode());
        }
        return builder.buildJSONString();
    }

    @PermissionRequired(value = "sys:scheduleJob:resume")
    @RequestMapping(value = "resume", method = RequestMethod.POST)
    public String resume(Long id) {
        ResponseBuilder builder = ResponseBuilder.newBuilder();
        try {

            scheduleJobHandler.resume(new Long[]{id});

            builder.message("success");
            builder.statusCode(200);
        } catch (HandlerException e) {
            builder.message(e.getMessage());
            builder.statusCode(e.getStatusCode());
        }
        return builder.buildJSONString();
    }

    @PermissionRequired(value = "sys:scheduleJob:pause")
    @RequestMapping(value = "pause", method = RequestMethod.POST)
    public String pause(Long id) {
        ResponseBuilder builder = ResponseBuilder.newBuilder();
        try {

            scheduleJobHandler.pause(new Long[]{id});

            builder.message("success");
            builder.statusCode(200);
        } catch (HandlerException e) {
            builder.message(e.getMessage());
            builder.statusCode(e.getStatusCode());
        }
        return builder.buildJSONString();
    }

    @PermissionRequired(value = "sys:scheduleJob:view")
    @RequestMapping(value = "view", method = RequestMethod.GET)
    public String view(Long id) {
        ResponseBuilder builder = ResponseBuilder.newBuilder();
        try {

            ScheduleJobBean bean = scheduleJobHandler.get(id);

            builder.result(bean);
            builder.message("success");
            builder.statusCode(200);
        } catch (HandlerException e) {
            builder.message(e.getMessage());
            builder.statusCode(e.getStatusCode());
        }
        return builder.buildJSONString();
    }
}
