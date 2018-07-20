package com.cn.web.job.dao;

import com.cn.web.job.domain.ScheduleJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("scheduleJobDao")
public interface ScheduleJobDao extends JpaRepository<ScheduleJob, Long> {
}
