package org.cn.web.admin.web.controller;

import com.cn.web.core.platform.exception.HandlerException;
import com.cn.web.core.platform.web.DefaultController;
import com.cn.web.core.platform.web.ResponseBuilder;
import com.cn.web.rbac.web.handler.ScheduleJobHandler;
import com.cn.web.rbac.web.request.ScheduleJobReq;
import com.cn.web.rbac.web.response.ScheduleJobResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "scheduleJob")
public class ScheduleJobController extends DefaultController {

    @Autowired
    ScheduleJobHandler scheduleJobHandler;

    // @PermissionRequired(value = "sys:task:view")
    @RequestMapping(value = "search", method = {RequestMethod.POST})
    public String search(String keyword, int page, /* @Max(20) */ int size) {
        ResponseBuilder.Builder builder = ResponseBuilder.newBuilder();
        try {

            Map<String, Object> result = scheduleJobHandler.search(keyword, page, size);

            builder.result(result);
            builder.message("success");
            builder.statusCode(200);
        } catch (HandlerException e) {
            builder.message(e.getMessage());
            builder.statusCode(e.getStatusCode());
        }
        return builder.buildJSONString();
    }

    // @PermissionRequired(value = "sys:task:save")
    @RequestMapping(value = "save", method = {RequestMethod.POST})
    public String save(@RequestBody ScheduleJobReq req) {
        ResponseBuilder.Builder builder = ResponseBuilder.newBuilder();
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

    // @PermissionRequired(value = "sys:task:update")
    @RequestMapping(value = "update", method = {RequestMethod.POST})
    public String update(@RequestBody ScheduleJobReq req) {
        ResponseBuilder.Builder builder = ResponseBuilder.newBuilder();
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

    // @PermissionRequired(value = "sys:task:delete")
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public String delete(String ids) {
        ResponseBuilder.Builder builder = ResponseBuilder.newBuilder();
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

    // @PermissionRequired(value = "sys:task:view")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(int page, /* @Max(20) */ int size) {
        ResponseBuilder.Builder builder = ResponseBuilder.newBuilder();
        try {

            Map<String, Object> result = scheduleJobHandler.list(page, size);

            builder.result(result);
            builder.message("success");
            builder.statusCode(200);
        } catch (HandlerException e) {
            builder.message(e.getMessage());
            builder.statusCode(e.getStatusCode());
        }
        return builder.buildJSONString();
    }

    // @PermissionRequired(value = "sys:task:start")
    @RequestMapping(value = "start", method = RequestMethod.POST)
    public String start(String id) {
        ResponseBuilder.Builder builder = ResponseBuilder.newBuilder();
        try {

            scheduleJobHandler.start(id);

            builder.message("success");
            builder.statusCode(200);
        } catch (HandlerException e) {
            builder.message(e.getMessage());
            builder.statusCode(e.getStatusCode());
        }
        return builder.buildJSONString();
    }

    // @PermissionRequired(value = "sys:task:pause")
    @RequestMapping(value = "pause", method = RequestMethod.POST)
    public String pause(String id) {
        ResponseBuilder.Builder builder = ResponseBuilder.newBuilder();
        try {

            scheduleJobHandler.pause(id);

            builder.message("success");
            builder.statusCode(200);
        } catch (HandlerException e) {
            builder.message(e.getMessage());
            builder.statusCode(e.getStatusCode());
        }
        return builder.buildJSONString();
    }

    // @PermissionRequired(value = "sys:task:resume")
    @RequestMapping(value = "resume", method = RequestMethod.POST)
    public String resume(String id) {
        ResponseBuilder.Builder builder = ResponseBuilder.newBuilder();
        try {

            scheduleJobHandler.resume(id);

            builder.message("success");
            builder.statusCode(200);
        } catch (HandlerException e) {
            builder.message(e.getMessage());
            builder.statusCode(e.getStatusCode());
        }
        return builder.buildJSONString();
    }

    // @PermissionRequired(value = "sys:task:view")
    @RequestMapping(value = "view", method = RequestMethod.GET)
    public String view() {
        ResponseBuilder.Builder builder = ResponseBuilder.newBuilder();
        try {

            builder.result("unimplemented");
            builder.message("success");
            builder.statusCode(200);
        } catch (HandlerException e) {
            builder.message(e.getMessage());
            builder.statusCode(e.getStatusCode());
        }
        return builder.buildJSONString();
    }
}
