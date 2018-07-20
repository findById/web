package com.cn.web.job.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "schedule_job")
public class ScheduleJob implements Serializable {

    public static final short FLAG_NORMAL = 0;
    public static final short FLAG_DELETE = 1;

    public static final int STATE_STANDBY = 0;
    public static final int STATE_RUNNING = 1;
    public static final int STATE_PAUSED = 2;
    public static final int STATE_STOPPED = 3;

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
    private Integer status = STATE_STANDBY;
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
