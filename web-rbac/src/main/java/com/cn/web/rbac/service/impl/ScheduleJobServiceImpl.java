package com.cn.web.rbac.service.impl;

import com.cn.web.rbac.dao.ScheduleJobDao;
import com.cn.web.rbac.domain.ScheduleJob;
import com.cn.web.rbac.service.ScheduleJobService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Service(value = "scheduleJobService")
public class ScheduleJobServiceImpl implements ScheduleJobService {

    @Resource
    ScheduleJobDao scheduleJobDao;

    @Override
    public Page<ScheduleJob> search(String keyword, Pageable page) {

        System.out.println(keyword);

        return scheduleJobDao.findAll(page);
    }

    @Override
    public ScheduleJob get(Serializable id) {
        Optional<ScheduleJob> optional = scheduleJobDao.findById(String.valueOf(id));
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
    public void delete(Serializable id) {
        scheduleJobDao.deleteById(String.valueOf(id));
    }

    @Override
    public List<ScheduleJob> list() {
        return scheduleJobDao.findAll();
    }

    @Override
    public Page<ScheduleJob> list(int offset, int size) {
        int page = offset;
        return scheduleJobDao.findAll(PageRequest.of(page, size, Sort.by("id")));
    }

}
