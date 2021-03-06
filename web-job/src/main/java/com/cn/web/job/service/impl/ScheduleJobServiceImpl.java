package com.cn.web.job.service.impl;

import com.alibaba.fastjson.JSON;
import com.cn.web.job.dao.ScheduleJobDao;
import com.cn.web.job.domain.ScheduleJob;
import com.cn.web.job.service.ScheduleJobService;
import com.cn.web.job.util.ScheduleJobUtils;
import org.quartz.CronExpression;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service(value = "scheduleJobService")
public class ScheduleJobServiceImpl implements ScheduleJobService {

    @Resource
    ScheduleJobDao scheduleJobDao;

    @Autowired
    private Scheduler scheduler;

    @PostConstruct
    public void init() {
        List<ScheduleJob> list = this.list();
        if (list == null || list.isEmpty()) {
            return;
        }
        for (ScheduleJob item : list) {
            System.out.println("schedule job id: " + item.getId() + ", method: " + item.getMethod() + ", cron: " + item.getCron());
            try {
                Trigger trigger = ScheduleJobUtils.getTrigger(scheduler, item);
                if (trigger == null) {
                    ScheduleJobUtils.createScheduleJob(scheduler, item);
                } else {
                    ScheduleJobUtils.updateScheduleJob(scheduler, item);
                }
                if (ScheduleJob.JOB_STATE_RUNNING.equals(item.getStatus())) {
                    ScheduleJobUtils.startScheduleJob(scheduler, item);
                }
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        }

        try {
            System.out.println("job: " + JSON.toJSONString(ScheduleJobUtils.getScheduleJob(scheduler)));
            System.out.println("running job: " + JSON.toJSONString(ScheduleJobUtils.getRunningJob(scheduler)));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ScheduleJob get(Long id) {
        Optional<ScheduleJob> optional = scheduleJobDao.findById(id);
        return optional.orElse(null);
    }

    @Override
    @Transactional
    public ScheduleJob save(ScheduleJob scheduleJob) {
        boolean valid = CronExpression.isValidExpression(scheduleJob.getCron());
        if (!valid) {
            throw new IllegalArgumentException("无效的Cron表达式");
        }
        scheduleJob.setStatus(ScheduleJob.JOB_STATE_STANDBY);
        scheduleJobDao.save(scheduleJob);

        ScheduleJobUtils.createScheduleJob(scheduler, scheduleJob);

        return scheduleJob;
    }

    @Override
    @Transactional
    public ScheduleJob update(ScheduleJob scheduleJob) {
        boolean valid = CronExpression.isValidExpression(scheduleJob.getCron());
        if (!valid) {
            throw new IllegalArgumentException("无效的Cron表达式");
        }
//         scheduleJobDao.update(scheduleJob);
        scheduleJobDao.save(scheduleJob);

        try {
            ScheduleJobUtils.updateScheduleJob(scheduler, scheduleJob);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }

        return scheduleJob;
    }

    @Override
    @Transactional
    public void delete(Long[] ids) {
        for (Long id : ids) {
            ScheduleJob job = get(id);
            try {
                ScheduleJobUtils.deleteScheduleJob(scheduler, job);
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
            scheduleJobDao.deleteById(id);
        }
    }

    @Override
    @Transactional
    public void deleteByLogic(Long[] ids) {
        for (Long id : ids) {
            ScheduleJob job = this.get(id);
            if (job != null) {
                try {
                    ScheduleJobUtils.deleteScheduleJob(scheduler, job);
                } catch (SchedulerException e) {
                    e.printStackTrace();
                }
                job.setDelFlg(ScheduleJob.FLAG_DELETE);
                scheduleJobDao.save(job);
            }
        }
    }

    @Override
    public List<ScheduleJob> list() {
        return scheduleJobDao.findAll();
    }

    @Override
    public Page<ScheduleJob> list(int page, int size) {
        return scheduleJobDao.findAll(PageRequest.of(page, size, Sort.by("id")));
    }

    @Override
    public Page<ScheduleJob> search(String keywords, int page, int size) {
        ScheduleJob item = new ScheduleJob();
        // item.setId(keywords);
        item.setName(keywords);
        item.setGroup(keywords);
        item.setMethod(keywords);
        item.setRemark(keywords);
        ExampleMatcher matcher = ExampleMatcher.matchingAny()
                //.withMatcher("id", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("group", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("method", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("remark", ExampleMatcher.GenericPropertyMatchers.contains())
                .withIgnorePaths("status", "updateTime", "delFlg");
        Example<ScheduleJob> example = Example.of(item, matcher);
        return scheduleJobDao.findAll(example, PageRequest.of(page, size, Sort.by("updateTime").descending()));
    }

    @Override
    @Transactional
    public void updateBatch(Long[] ids, String state) throws Exception {
        for (Long id : ids) {
            ScheduleJob job = get(id);
            switch (state) {
                case ScheduleJob.JOB_STATE_STANDBY: {
                    ScheduleJobUtils.pauseScheduleJob(scheduler, job);
                    break;
                }
                case ScheduleJob.JOB_STATE_RUNNING: {
                    ScheduleJobUtils.startScheduleJob(scheduler, job);
                    break;
                }
                case ScheduleJob.JOB_STATE_PAUSED: {
                    ScheduleJobUtils.pauseScheduleJob(scheduler, job);
                    break;
                }
                case ScheduleJob.JOB_STATE_STOPPED: {
                    ScheduleJobUtils.pauseScheduleJob(scheduler, job);
                    break;
                }
                default:
                    return;
            }
            job.setStatus(state);
            scheduleJobDao.save(job);
        }

        try {
            System.out.println("job: " + JSON.toJSONString(ScheduleJobUtils.getScheduleJob(scheduler)));
            System.out.println("running job: " + JSON.toJSONString(ScheduleJobUtils.getRunningJob(scheduler)));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public boolean start(Long[] ids) {
        for (Long id : ids) {
            try {
                ScheduleJob job = get(id);
                ScheduleJobUtils.startScheduleJob(scheduler, job);
                job.setStatus(ScheduleJob.JOB_STATE_RUNNING);
                scheduleJobDao.save(job);
            } catch (SchedulerException e) {
                e.printStackTrace();
                return false;
            }
        }

        try {
            System.out.println("job: " + JSON.toJSONString(ScheduleJobUtils.getScheduleJob(scheduler)));
            System.out.println("running job: " + JSON.toJSONString(ScheduleJobUtils.getRunningJob(scheduler)));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    @Transactional
    public boolean resume(Long[] ids) {
        for (Long id : ids) {
            try {
                ScheduleJob job = get(id);
                ScheduleJobUtils.resumeScheduleJob(scheduler, job);
                job.setStatus(ScheduleJob.JOB_STATE_RUNNING);
                scheduleJobDao.save(job);
            } catch (SchedulerException e) {
                e.printStackTrace();
                return false;
            }
        }

        try {
            System.out.println("job: " + JSON.toJSONString(ScheduleJobUtils.getScheduleJob(scheduler)));
            System.out.println("running job: " + JSON.toJSONString(ScheduleJobUtils.getRunningJob(scheduler)));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    @Transactional
    public boolean pause(Long[] ids) {
        for (Long id : ids) {
            try {
                ScheduleJob job = get(id);
                ScheduleJobUtils.pauseScheduleJob(scheduler, job);
                job.setStatus(ScheduleJob.JOB_STATE_PAUSED);
                scheduleJobDao.save(job);
            } catch (SchedulerException e) {
                e.printStackTrace();
                return false;
            }
        }

        try {
            System.out.println("job: " + JSON.toJSONString(ScheduleJobUtils.getScheduleJob(scheduler)));
            System.out.println("running job: " + JSON.toJSONString(ScheduleJobUtils.getRunningJob(scheduler)));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    @Transactional
    public boolean stop(Long[] ids) {
        for (Long id : ids) {
            try {
                ScheduleJob job = get(id);
                ScheduleJobUtils.pauseScheduleJob(scheduler, job);
                job.setStatus(ScheduleJob.JOB_STATE_STOPPED);
                scheduleJobDao.save(job);
            } catch (SchedulerException e) {
                e.printStackTrace();
                return false;
            }
        }

        try {
            System.out.println("job: " + JSON.toJSONString(ScheduleJobUtils.getScheduleJob(scheduler)));
            System.out.println("running job: " + JSON.toJSONString(ScheduleJobUtils.getRunningJob(scheduler)));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return true;
    }
}
