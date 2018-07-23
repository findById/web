package com.cn.web.job.service.impl;

import com.cn.web.job.dao.ScheduleJobDao;
import com.cn.web.job.domain.ScheduleJob;
import com.cn.web.job.service.ScheduleJobService;
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

    @PostConstruct
    public void init() {
        List<ScheduleJob> list = this.list();
        if (list == null || list.isEmpty()) {
            return;
        }
        for (ScheduleJob item : list) {
            System.out.println("schedule job id: " + item.getId() + ", cron: " + item.getCron());
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
    public void delete(Long id) {
        scheduleJobDao.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteByLogic(Long id) {
        ScheduleJob job = this.get(id);
        if (job != null) {
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
        item.setRemark(keywords);
        ExampleMatcher matcher = ExampleMatcher.matchingAny()
                //.withMatcher("id", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("remark", ExampleMatcher.GenericPropertyMatchers.contains())
                .withIgnorePaths("status", "updateTime", "delFlg");
        Example<ScheduleJob> example = Example.of(item, matcher);
        return scheduleJobDao.findAll(example, PageRequest.of(page, size, Sort.by("updateTime").descending()));
    }

    @Override
    public void updateBatch(Long[] ids, int state) {

    }

    @Override
    public boolean start(Long[] ids) {

        updateBatch(ids, ScheduleJob.STATE_RUNNING);
        return true;
    }

    @Override
    public boolean resume(Long[] ids) {

        updateBatch(ids, ScheduleJob.STATE_RUNNING);
        return true;
    }

    @Override
    public boolean pause(Long[] ids) {

        updateBatch(ids, ScheduleJob.STATE_PAUSED);
        return true;
    }

    @Override
    public boolean stop(Long[] ids) {

        updateBatch(ids, ScheduleJob.STATE_STOPPED);
        return true;
    }
}
