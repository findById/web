package ${packageName}.${moduleName}.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by ${author!""} on ${date!""}.
 */
@Entity
@Table(name = "${entityName}")
public class ${ClassName} implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    @Column(name = "version")
    private Integer version;

    @Column(name = "del_flg")
    private Integer delFlg = 0;

    @Column(name = "update_time")
    private Long updateTime = System.currentTimeMillis();

    @Column(name = "description", length = 200)
    private String description;

    public ${ClassName}() {
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

    public Integer getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(Integer delFlg) {
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
