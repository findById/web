package org.cn.web.generator.domain;

import java.util.List;

public class Module {
    private String type;
    private String templateId;

    private String author;
    private String email;

    private String basePackage;
    private String moduleName;
    private String className;

    private List<ModuleColumn> columns;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<ModuleColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<ModuleColumn> columns) {
        this.columns = columns;
    }
}
