package ${packageName}.${moduleName}.web.request;

import java.io.Serializable;

/**
 * Created by ${author!""} on ${date!""}.
 */
public class ${ClassName}Req implements Serializable {
    /**
     * id
     */
    private String id;
<#if columns??>
    <#list columns as item>
    /**
     * ${item.desc!""}
     */
    private ${item.type} ${item.name};
    </#list>
</#if>

    public ${ClassName}Req() {
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
