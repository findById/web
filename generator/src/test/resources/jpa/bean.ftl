package ${packageName}.${moduleName}.web.vo;

import java.io.Serializable;

/**
 * Created by ${author!""} on ${date!""}.
 */
public class ${ClassName}Bean implements Serializable {
    private String id;
<#if columns??>
    <#list columns as item>
    private ${item.type} ${item.name};
    </#list>
</#if>

    public ${ClassName}Bean() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
