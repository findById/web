package ${packageName}.${moduleName}.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by ${author!""} on ${date!""}.
 */
@Entity
@Table(name = "${entityName}")
public class ${ClassName} implements Serializable {

<#if columns??>
    <#list columns as item>
    /**
     * ${item.desc!""}
     */
    @Column(name = "${item.name}")
    private ${item.type} ${item.name};
    </#list>
</#if>

    public ${ClassName}() {
    }

<#if columns??>
    <#list columns as item>
    public ${item.type!"String"} get${item.name?capitalize}() {
        return ${item.name!""};
    }

    public void set${item.name?capitalize}(${item.type!"String"} ${item.name!""}) {
        this.${item.name!""} = ${item.name!""};
    }

    </#list>
</#if>
}
