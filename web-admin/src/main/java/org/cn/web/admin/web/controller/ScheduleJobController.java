package org.cn.web.admin.web.controller;

import com.cn.web.core.platform.web.DefaultController;
import com.cn.web.rbac.web.handler.ScheduleJobHandler;
import com.cn.web.rbac.web.request.ScheduleJobReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "scheduleJob")
public class ScheduleJobController extends DefaultController {

    @Autowired
    ScheduleJobHandler scheduleJobHandler;

    // @PermissionRequired(value = "sys:task:view")
    @RequestMapping(value = "search", method = {RequestMethod.POST})
    public String search(String keyword, int page, /* @Max(20) */ int size) {
        return scheduleJobHandler.search(keyword, page, size);
    }

    // @PermissionRequired(value = "sys:task:save")
    @RequestMapping(value = "save", method = {RequestMethod.POST})
    public String save(@RequestBody ScheduleJobReq req) {
        return scheduleJobHandler.save(req);
    }

    // @PermissionRequired(value = "sys:task:update")
    @RequestMapping(value = "update", method = {RequestMethod.POST})
    public String update(@RequestBody ScheduleJobReq req) {
        return scheduleJobHandler.update(req);
    }

    // @PermissionRequired(value = "sys:task:delete")
    @RequestMapping(value = "delete")
    public String delete(String ids) {
        return scheduleJobHandler.delete(ids);
    }

    // @PermissionRequired(value = "sys:task:view")
    @RequestMapping(value = "list")
    public String list(int page, /* @Max(20) */ int size) {
        return scheduleJobHandler.list(page, size);
    }

    // @PermissionRequired(value = "sys:task:start")
    @RequestMapping(value = "start", method = RequestMethod.POST)
    public String start(String id) {
        return scheduleJobHandler.start(id);
    }

    // @PermissionRequired(value = "sys:task:pause")
    @RequestMapping(value = "pause", method = RequestMethod.POST)
    public String pause(String id) {
        return scheduleJobHandler.pause(id);
    }

    // @PermissionRequired(value = "sys:task:resume")
    @RequestMapping(value = "resume", method = RequestMethod.POST)
    public String resume(String id) {
        return scheduleJobHandler.resume(id);
    }

    // @PermissionRequired(value = "sys:task:view")
    @RequestMapping(value = "view")
    public String view() {
        return "unimplemented";
    }
}
