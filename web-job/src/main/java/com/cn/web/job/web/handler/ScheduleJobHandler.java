package com.cn.web.job.web.handler;

import com.cn.web.core.platform.exception.HandlerException;
import com.cn.web.job.domain.ScheduleJob;
import com.cn.web.job.service.ScheduleJobService;
import com.cn.web.job.web.request.ScheduleJobReq;
import com.cn.web.job.web.response.ScheduleJobResp;
import com.cn.web.job.web.vo.ScheduleJobBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("scheduleJobHandler")
public class ScheduleJobHandler {

    @Autowired
    ScheduleJobService scheduleJobService;

    public Map<String, Object> search(String keywords, int page, int size) {

        Page<ScheduleJob> list = scheduleJobService.search(keywords, page - 1, size);

        List<ScheduleJobResp> beanList = new ArrayList<>();
        if (list.hasContent()) {
            for (ScheduleJob scheduleJob : list.getContent()) {
                if (scheduleJob.getDelFlg() == ScheduleJob.FLAG_DELETE) {
                    continue;
                }
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
        scheduleJob.setName(req.getName());
        scheduleJob.setCron(req.getCron());
        scheduleJob.setMethod(req.getMethod());
        scheduleJob.setParams(req.getParams());
        scheduleJob.setRemark(req.getRemark());

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
        if (req.getName() != null && !req.getName().isEmpty()) {
            scheduleJob.setName(req.getName());
        }
        if (req.getCron() != null && !req.getCron().isEmpty()) {
            scheduleJob.setCron(req.getCron());
        }
        if (req.getMethod() != null && !req.getMethod().isEmpty()) {
            scheduleJob.setMethod(req.getMethod());
        }
        if (req.getParams() != null && !req.getParams().isEmpty()) {
            scheduleJob.setParams(req.getParams());
        }
        if (req.getRemark() != null && !req.getRemark().isEmpty()) {
            scheduleJob.setRemark(req.getRemark());
        }

        scheduleJobService.update(scheduleJob);

        return true;
    }

    public boolean delete(Long[] ids) {

        scheduleJobService.delete(ids);

        return true;
    }

    public Map<String, Object> list(int page, int size) {

        Page<ScheduleJob> list = scheduleJobService.list(page - 1, size);

        List<ScheduleJobResp> beanList = new ArrayList<>();
        if (list.hasContent()) {
            for (ScheduleJob scheduleJob : list.getContent()) {
                if (scheduleJob.getDelFlg() == ScheduleJob.FLAG_DELETE) {
                    continue;
                }
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

    public ScheduleJobBean get(Long id) {
        ScheduleJob scheduleJob = scheduleJobService.get(id);
        if (scheduleJob != null && scheduleJob.getDelFlg() != ScheduleJob.FLAG_DELETE) {
            ScheduleJobBean bean = new ScheduleJobBean();
            BeanUtils.copyProperties(scheduleJob, bean);
            return bean;
        }
        return null;
    }

    public boolean start(Long[] ids) {
        return scheduleJobService.start(ids);
    }

    public boolean resume(Long[] ids) {
        return scheduleJobService.resume(ids);
    }

    public boolean pause(Long[] ids) {
        return scheduleJobService.pause(ids);
    }

    public boolean stop(Long[] ids) {
        return scheduleJobService.stop(ids);
    }
}
