package com.cn.web.rbac.web.handler;

import com.cn.web.core.platform.exception.HandlerException;
import com.cn.web.rbac.domain.ScheduleJob;
import com.cn.web.rbac.service.ScheduleJobService;
import com.cn.web.rbac.web.request.ScheduleJobReq;
import com.cn.web.rbac.web.response.ScheduleJobResp;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("scheduleJobHandler")
public class ScheduleJobHandler {

    @Autowired
    ScheduleJobService scheduleJobService;

    public Map<String, Object> search(String keyword, int page, /* @Max(20) */ int size) {
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

        return result;
    }

    public ScheduleJobResp save(ScheduleJobReq req) {
        if (req == null) {
            throw new HandlerException(201, "Request body must not be null.");
        }

        ScheduleJob scheduleJob = new ScheduleJob();
        // TODO copy req

        scheduleJobService.save(scheduleJob);

        ScheduleJobResp resp = new ScheduleJobResp();
        BeanUtils.copyProperties(scheduleJob, resp);

        return resp;
    }

    public boolean update(ScheduleJobReq req) {

        if (req == null || req.getId() == null) {
            throw new HandlerException(201, "ScheduleJob not exists");
        }

        ScheduleJob scheduleJob = scheduleJobService.get(req.getId());
        if (scheduleJob == null) {
            throw new HandlerException(201, "ScheduleJob not exists");
        }
        // TODO copy value

        scheduleJobService.update(scheduleJob);

        return true;
    }

    public boolean delete(String ids) {

        scheduleJobService.delete(ids);

        return true;
    }

    public Map<String, Object> list(int page, /* @Max(20) */ int size) {
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

        return result;
    }

    public boolean start(String id) {
        return true;
    }

    public boolean pause(String id) {
        return true;
    }

    public boolean resume(String id) {
        return true;
    }
}
