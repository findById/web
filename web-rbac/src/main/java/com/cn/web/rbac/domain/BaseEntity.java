package com.cn.web.rbac.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public class BaseEntity implements Serializable {

    public static final short FLAG_NORMAL = 0;
    public static final short FLAG_ENABLE = 1;
    public static final short FLAG_DISABLED = 2;
    public static final short FLAG_DELETE = 3;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id", length = 50, unique = true, nullable = false)
    private String id;

    @Version
    @Column(name = "version")
    private Integer version;

    @Column(name = "del_flg", length = 2)
    private Short delFlg = FLAG_NORMAL;

    @Column(name = "update_time")
    private Long updateTime = System.currentTimeMillis();

    @Column(name = "description", length = 200)
    private String description;

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

}
