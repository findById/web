package com.cn.web.rbac.web.response;

import java.io.Serializable;

public class ScheduleJobResp implements Serializable {
    private String id;
    private String name;
    private String method;
    private String params;
    private Short cron;
    private Integer status;
    private String remark;

    public ScheduleJobResp() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Short getCron() {
        return cron;
    }

    public void setCron(Short cron) {
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
