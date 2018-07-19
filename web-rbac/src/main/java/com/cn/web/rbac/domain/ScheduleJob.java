package com.cn.web.rbac.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "schedule_job")
public class ScheduleJob extends BaseEntity {
    /**
     * job name
     */
    @Column(name = "name", length = 50)
    private String name;
    /**
     * job method
     */
    @Column(name = "method", length = 50)
    private String method;
    /**
     * job parameter
     */
    @Column(name = "params", length = 200)
    private String params;
    /**
     * cron expression
     */
    @Column(name = "cron", length = 100)
    private String cron;
    /**
     * 0:Standby 1:Running 2:Paused 3:Stopped
     */
    @Column(name = "status", length = 2)
    private Integer status;
    /**
     * remark
     */
    @Column(name = "remark", length = 200)
    private String remark;

    public ScheduleJob() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
