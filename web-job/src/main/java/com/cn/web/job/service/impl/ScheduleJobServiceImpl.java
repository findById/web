package com.cn.web.job.service.impl;

import com.cn.web.job.dao.ScheduleJobDao;
import com.cn.web.job.domain.ScheduleJob;
import com.cn.web.job.service.ScheduleJobService;
import com.cn.web.job.util.ScheduleJobUtils;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
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
//        ScheduleJob job = new ScheduleJob();
//        job.setName("test job");
//        job.setGroup("test job");
//        job.setMethod(TestJob.class.getName());
//        job.setCron("0/10 * * * * ?");
//        ScheduleJobUtils.createScheduleJob(scheduler, job);
        for (ScheduleJob item : list) {
            System.out.println("schedule job id: " + item.getId() + ", cron: " + item.getCron());
            ScheduleJobUtils.createScheduleJob(scheduler, item);
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

        scheduleJobDao.save(scheduleJob);

        return scheduleJob;
    }

    @Override
    @Transactional
    public ScheduleJob update(ScheduleJob scheduleJob) {
        // scheduleJobDao.update(scheduleJob);
        scheduleJobDao.save(scheduleJob);
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
    public void deleteByLogic(Long id) {
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
        return true;
    }
}
