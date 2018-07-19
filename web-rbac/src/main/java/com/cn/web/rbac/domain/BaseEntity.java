package com.cn.web.rbac.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public class BaseEntity implements Serializable {

    public static final short FLAG_NORMAL = 0;
    public static final short FLAG_DELETE = 1;

    public static final short STATE_ENABLE = 0;
    public static final short STATE_DISABLED = 1;
    public static final short STATE_EXPIRED = 2;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id", length = 50, unique = true, nullable = false)
    private String id;
    @Version
    @Column(name = "version")
    private Short version;
    @Column(name = "del_flg", length = 2)
    private Short delFlg = FLAG_NORMAL;
    @Column(name = "update_time")
    private Long updateTime = System.currentTimeMillis();
    @Column(name = "description", length = 200)
    private String description;
    @Column(name = "state", length = 1)
    private Short state = STATE_ENABLE;

    public BaseEntity() {
    }

    public BaseEntity(String description) {
        this.description = description;
    }

    public BaseEntity(String id, String description) {
        this.id = id;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
}
