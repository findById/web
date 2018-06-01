package org.cn.web.generator.service.impl;

import org.cn.web.generator.domain.Module;
import org.cn.web.generator.service.GeneratorService;
import org.cn.web.generator.util.FreeMarkerImpl;
import org.cn.web.generator.util.GeneratorUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeneratorServiceImpl implements GeneratorService {

    @Override
    public boolean generate(File root, Module module) {
        if (module.getBasePackage() == null) {
            System.out.println("'basePackage' must not be null.");
            return false;
        }
        if (module.getModuleName() == null) {
            System.out.println("'moduleName' must not be null.");
            return false;
        }
        if (module.getClassName() == null) {
            System.out.println("'className' must not be null.");
            return false;
        }

        if (module.getTemplateId() == null) {
            module.setTemplateId("template");
        }
        String templateDir = new File(GeneratorServiceImpl.class.getResource("/").getPath(), "../resources/" + module.getTemplateId()).getPath();

        // module path
        while (root.getPath().contains("/src/main") || root.getPath().contains("/src")) {
            root = root.getParentFile();
        }
        System.out.println("Project Path: " + root);
        System.out.println("Template Path: " + templateDir);

        // Java file dir
        String javaPath = (root + "/src/main/java/" + module.getBasePackage()).replaceAll("[\\/\\.]", "\\/");
        System.out.println("Java Path: " + javaPath);

        // param
        Map<String, Object> model = new HashMap<>();
        model.put("packageName", module.getBasePackage().toLowerCase());
        model.put("moduleName", module.getModuleName().toLowerCase());
        model.put("ClassName", GeneratorUtils.capitalize(module.getClassName()));
        model.put("className", GeneratorUtils.uncapitalize(module.getClassName()));
        model.put("author", module.getAuthor() == null ? System.getProperty("user.name", "") : module.getAuthor());
        model.put("date", new SimpleDateFormat("MM/dd/yyyy").format(System.currentTimeMillis()));
        model.put("entityName", model.get("moduleName") + "_" + model.get("className"));
        model.put("permissionPrefix", model.get("moduleName") + ":" + model.get("className"));

        // FreeMarker initial
        FreeMarkerImpl freeMarker = new FreeMarkerImpl(templateDir);

        String content;
        String filePath;

        // Create folder
        List<String> pathList = new ArrayList<>();
        pathList.add(javaPath + "/" + model.get("moduleName") + "/web/controller/");
        pathList.add(javaPath + "/" + model.get("moduleName") + "/web/request/");
        pathList.add(javaPath + "/" + model.get("moduleName") + "/web/response/");
        pathList.add(javaPath + "/" + model.get("moduleName") + "/web/interceptor/");
        pathList.add(javaPath + "/" + model.get("moduleName") + "/web/converter/");
        pathList.add(javaPath + "/" + model.get("moduleName") + "/web/vo/");

        pathList.add(javaPath + "/" + model.get("moduleName") + "/service/impl/");
        pathList.add(javaPath + "/" + model.get("moduleName") + "/dao/impl/");
        pathList.add(javaPath + "/" + model.get("moduleName") + "/domain/");
        pathList.add(javaPath + "/" + model.get("moduleName") + "/repository/");
        for (String path : pathList) {
            GeneratorUtils.createFolder(path);
        }

        // Entity
        content = freeMarker.renderTemplate("entity.ftl", model);
        if (content != null) {
            filePath = javaPath + "/" + model.get("moduleName") + "/domain/" + model.get("ClassName") + ".java";
            GeneratorUtils.writeFile(content, filePath);
            System.out.println("Entity: " + filePath);
        }

        // Dao
        content = freeMarker.renderTemplate("dao.ftl", model);
        if (content != null) {
            filePath = javaPath + "/" + model.get("moduleName") + "/dao/" + model.get("ClassName") + "Dao.java";
            GeneratorUtils.writeFile(content, filePath);
            System.out.println("Dao: " + filePath);
        }

        // DaoImpl
        content = freeMarker.renderTemplate("daoImpl.ftl", model);
        if (content != null) {
            filePath = javaPath + "/" + model.get("moduleName") + "/dao/impl/" + model.get("ClassName") + "DaoImpl.java";
            GeneratorUtils.writeFile(content, filePath);
            System.out.println("DaoImpl: " + filePath);
        }

        // Repository
        content = freeMarker.renderTemplate("repository.ftl", model);
        if (content != null) {
            filePath = javaPath + "/" + model.get("moduleName") + "/repository/" + model.get("ClassName") + "Repository.java";
            GeneratorUtils.writeFile(content, filePath);
            System.out.println("DaoImpl: " + filePath);
        }

        // Service
        content = freeMarker.renderTemplate("service.ftl", model);
        if (content != null) {
            filePath = javaPath + "/" + model.get("moduleName") + "/service/" + model.get("ClassName") + "Service.java";
            GeneratorUtils.writeFile(content, filePath);
            System.out.println("Service: " + filePath);
        }

        // ServiceImpl
        content = freeMarker.renderTemplate("serviceImpl.ftl", model);
        if (content != null) {
            filePath = javaPath + "/" + model.get("moduleName") + "/service/impl/" + model.get("ClassName") + "ServiceImpl.java";
            GeneratorUtils.writeFile(content, filePath);
            System.out.println("ServiceImpl: " + filePath);
        }

        // Value Object
        content = freeMarker.renderTemplate("bean.ftl", model);
        if (content != null) {
            filePath = javaPath + "/" + model.get("moduleName") + "/web/vo/" + model.get("ClassName") + "Bean.java";
            GeneratorUtils.writeFile(content, filePath);
            System.out.println("Value Object: " + filePath);
        }

        // Request Object
        content = freeMarker.renderTemplate("request.ftl", model);
        if (content != null) {
            filePath = javaPath + "/" + model.get("moduleName") + "/web/request/" + model.get("ClassName") + "Req.java";
            GeneratorUtils.writeFile(content, filePath);
            System.out.println("Request Object: " + filePath);
        }

        // Response Object
        content = freeMarker.renderTemplate("response.ftl", model);
        if (content != null) {
            filePath = javaPath + "/" + model.get("moduleName") + "/web/response/" + model.get("ClassName") + "Resp.java";
            GeneratorUtils.writeFile(content, filePath);
            System.out.println("Response Object: " + filePath);
        }

        // Controller
        content = freeMarker.renderTemplate("controller.ftl", model);
        if (content != null) {
            filePath = javaPath + "/" + model.get("moduleName") + "/web/controller/" + model.get("ClassName") + "Controller.java";
            GeneratorUtils.writeFile(content, filePath);
            System.out.println("Controller: " + filePath);
        }

        System.out.println("Generate Success.");

        return true;
    }

}