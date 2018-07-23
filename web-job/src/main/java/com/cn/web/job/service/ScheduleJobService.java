package com.cn.web.job.service;

import com.cn.web.job.domain.ScheduleJob;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ScheduleJobService {

    ScheduleJob get(Long id);

    ScheduleJob save(ScheduleJob scheduleJob);

    ScheduleJob update(ScheduleJob scheduleJob);

    void delete(Long id);

    void deleteByLogic(Long id);

    List<ScheduleJob> list();

    Page<ScheduleJob> list(int page, int size);

    Page<ScheduleJob> search(String keywords, int page, int size);

    void updateBatch(Long[] ids, int state);

    boolean start(Long[] ids);

    boolean resume(Long[] ids);

    boolean pause(Long[] ids);

    boolean stop(Long[] ids);
}
