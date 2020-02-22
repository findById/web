package com.cn.web.rbac.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public class BaseEntity implements Serializable {

    public static final short FLAG_NORMAL = 0; // 删除标记-未删除
    public static final short FLAG_DISABLED = 1; // 删除标记-禁用
    public static final short FLAG_LOGIC_DELETE = 2; // 删除标记-逻辑删除
    public static final short FLAG_DELETE = 3; // 删除标记-待物理删除

    public static final short STATE_ENABLED = 0; // 状态-正常
    public static final short STATE_DISABLED = 1; // 状态-禁用
    public static final short STATE_EXPIRED = 2; // 状态-已过期

    /**
     * Id
     */
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id", unique = true, nullable = false)
//    private Long id;
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id", length = 50, unique = true, nullable = false)
    private String id;
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
}
