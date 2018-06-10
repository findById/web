package com.cn.web.rbac.service;

import com.cn.web.rbac.domain.ScheduleJob;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

public interface ScheduleJobService {

    Page<ScheduleJob> search(String keyword, Pageable page);

    ScheduleJob get(Serializable id);

    ScheduleJob save(ScheduleJob scheduleJob);

    ScheduleJob update(ScheduleJob scheduleJob);

    void delete(Serializable id);

    List<ScheduleJob> list();

    Page<ScheduleJob> list(int offset, int size);

}
