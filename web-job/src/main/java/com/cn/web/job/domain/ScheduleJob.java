package com.cn.web.job.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "schedule_job")
public class ScheduleJob implements Serializable {

    public static final short FLAG_NORMAL = 0; // 删除标记-未删除
    public static final short FLAG_DISABLED = 1; // 删除标记-禁用
    public static final short FLAG_LOGIC_DELETE = 2; // 删除标记-逻辑删除
    public static final short FLAG_DELETE = 3; // 删除标记-待物理删除

    public static final short STATE_ENABLED = 0; // 状态-正常
    public static final short STATE_DISABLED = 1; // 状态-禁用
    public static final short STATE_EXPIRED = 2; // 状态-已过期

    public static final String JOB_STATE_STANDBY = "0";
    public static final String JOB_STATE_RUNNING = "1";
    public static final String JOB_STATE_PAUSED = "2";
    public static final String JOB_STATE_STOPPED = "3";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    /**
     * 更新次数
     */
    @Version
    @Column(name = "version")
    private Integer version;
    /**
     * 删除标志 0:未删除 1:禁用 2:逻辑删除 3:待物理删除
     * ${FLAG_NORMAL} ${FLAG_DISABLED} ${FLAG_LOGIC_DELETE} ${FLAG_DELETE}
     */
    @Column(name = "del_flg", length = 2)
    private Short delFlg = FLAG_NORMAL;
    /**
     * 创建者
     */
    @Column(name = "create_by", length = 50)
    private String createBy;
    /**
     * 创建时间 create_at
     */
    @Column(name = "create_time")
    private Long createTime = System.currentTimeMillis();
    /**
     * 更新者
     */
    @Column(name = "update_by", length = 50)
    private String updateBy;
    /**
     * 更新时间 update_at
     */
    @Column(name = "update_time")
    private Long updateTime = System.currentTimeMillis();
    /**
     * 描述信息
     */
    @Column(name = "description", length = 200)
    private String description;
    /**
     * 状态 0:正常 1:禁用 2:过期
     * ${STATE_ENABLED} ${STATE_DISABLED} ${STATE_EXPIRED}
     */
    @Column(name = "state", length = 1)
    private Short state = STATE_ENABLED;
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Short getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(Short delFlg) {
        this.delFlg = delFlg;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
