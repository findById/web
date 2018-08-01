package com.cn.web.job.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "schedule_job")
public class ScheduleJob implements Serializable {

    public static final short FLAG_NORMAL = 0;
    public static final short FLAG_DELETE = 1;

    public static final short STATE_ENABLE = 0;
    public static final short STATE_DISABLED = 1;

    public static final String JOB_STATE_STANDBY = "0";
    public static final String JOB_STATE_RUNNING = "1";
    public static final String JOB_STATE_PAUSED = "2";
    public static final String JOB_STATE_STOPPED = "3";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    @Version
    @Column(name = "version")
    private Short version;
    @Column(name = "del_flg", length = 2)
    private Short delFlg = FLAG_NORMAL;
    @Column(name = "update_time")
    private Long updateTime = System.currentTimeMillis();
    @Column(name = "state", length = 1)
    private Short state = STATE_ENABLE;
    /**
     * job name
     */
    @Column(name = "job_name", length = 50)
    private String name;
    /**
     * job name
     */
    @Column(name = "job_group", length = 50)
    private String group;
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
    @Column(name = "cron_expression", length = 100)
    private String cron;
    /**
     * 0:Standby 1:Running 2:Paused 3:Stopped
     */
    @Column(name = "status", length = 2)
    private String status = JOB_STATE_STANDBY;
    /**
     * remark
     */
    @Column(name = "remark", length = 200)
    private String remark;

    public ScheduleJob() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Short getVersion() {
        return version;
    }

    public void setVersion(Short version) {
        this.version = version;
    }

    public Short getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(Short delFlg) {
        this.delFlg = delFlg;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Short getState() {
        return state;
    }

    public void setState(Short state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
