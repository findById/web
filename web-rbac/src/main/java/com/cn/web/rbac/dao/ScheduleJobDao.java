package com.cn.web.rbac.dao;

import com.cn.web.rbac.domain.ScheduleJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("scheduleJobDao")
public interface ScheduleJobDao extends JpaRepository<ScheduleJob, String> {
}
