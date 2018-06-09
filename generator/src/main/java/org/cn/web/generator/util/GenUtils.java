package org.cn.web.generator.util;

import org.cn.web.generator.domain.Module;
import org.cn.web.generator.service.impl.GeneratorServiceImpl;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenUtils {

    private static String uncapitalize(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        return String.valueOf(Character.toLowerCase(str.charAt(0))) + str.substring(1);
    }

    private static String capitalize(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        return String.valueOf(Character.toTitleCase(str.charAt(0))) + str.substring(1);
    }

    private static boolean createFile(String path) {
        /* --- createFile --- */
        File file = createParentFile(path);
        if (file != null && !file.exists()) {
            try {
                return file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    private static File createParentFile(String path) {
        /* --- createFolder --- */
        try {
            File file = new File(path);
            File parent = new File(file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf(File.separator)));
            if (!parent.exists()) {
                createParentFile(parent.getPath());
                parent.mkdirs();
            }
            return file;
        } catch (Exception ignored) {
        }
        return null;
    }

    private static void writeFile(String content, String path) {
        try {
            if (createFile(path)) {
                FileOutputStream fos = new FileOutputStream(path);
                OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
                BufferedWriter bw = new BufferedWriter(osw);
                bw.write(content);
                bw.close();
                osw.close();
            } else {
                System.out.println("file is exists.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<String> getJavaTemplateList(String templateId) {
        List<String> list = new ArrayList<>();
        list.add("entity.ftl");
        list.add("dao.ftl");
        list.add("daoImpl.ftl");
        list.add("repository.ftl");
        list.add("service.ftl");
        list.add("serviceImpl.ftl");
        list.add("bean.ftl");
        list.add("request.ftl");
        list.add("response.ftl");
        list.add("controller.ftl");
        return list;
    }

    private static String getJavaFilePath(String template, String basePackage, String module, String clazz) {
        String javaPath = ("/src/main/java/" + basePackage).replaceAll("[\\/\\.]", "\\/");

        if (template.endsWith("entity.ftl")) {
            return javaPath + "/" + module + "/domain/" + clazz + ".java";
        }
        if (template.endsWith("dao.ftl")) {
            return javaPath + "/" + module + "/dao/" + clazz + "Dao.java";
        }
        if (template.endsWith("daoImpl.ftl")) {
            return javaPath + "/" + module + "/dao/impl/" + clazz + "DaoImpl.java";
        }
        if (template.endsWith("repository.ftl")) {
            return javaPath + "/" + module + "/repository/" + clazz + "Repository.java";
        }
        if (template.endsWith("service.ftl")) {
            return javaPath + "/" + module + "/service/" + clazz + "Service.java";
        }
        if (template.endsWith("serviceImpl.ftl")) {
            return javaPath + "/" + module + "/service/impl/" + clazz + "ServiceImpl.java";
        }
        if (template.endsWith("bean.ftl")) {
            return javaPath + "/" + module + "/web/vo/" + clazz + "Bean.java";
        }
        if (template.endsWith("request.ftl")) {
            return javaPath + "/" + module + "/web/request/" + clazz + "Req.java";
        }
        if (template.endsWith("response.ftl")) {
            return javaPath + "/" + module + "/web/response/" + clazz + "Resp.java";
        }
        if (template.endsWith("controller.ftl")) {
            return javaPath + "/" + module + "/web/controller/" + clazz + "Controller.java";
        }
        return null;
    }

    public static boolean generator(File root, Module module) {
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

        // module data
        Map<String, Object> model = new HashMap<>();
        model.put("packageName", module.getBasePackage().toLowerCase());
        model.put("moduleName", module.getModuleName().toLowerCase());
        model.put("ClassName", capitalize(module.getClassName()));
        model.put("className", uncapitalize(module.getClassName()));
        model.put("entityName", model.get("moduleName") + "_" + model.get("className"));
        model.put("columns", module.getColumns());
        model.put("permissionPrefix", model.get("moduleName") + ":" + model.get("className"));

        model.put("author", module.getAuthor() == null ? System.getProperty("user.name", "") : module.getAuthor());
        model.put("email", module.getEmail());
        model.put("date", new SimpleDateFormat("MM/dd/yyyy").format(System.currentTimeMillis()));

        // FreeMarker initial
        FreeMarkerImpl freeMarker = new FreeMarkerImpl(templateDir);
        // generate java code
        List<String> temp = getJavaTemplateList("jpa");
        for (String t : temp) {
            String path = root + getJavaFilePath(t, model.get("packageName").toString(), model.get("moduleName").toString(), model.get("ClassName").toString());
            System.out.println(t);
            String content = freeMarker.renderTemplate(t, model);
            if (content != null) {
                writeFile(content, path);
                System.out.println(">>" + path);
            }
        }
        System.out.println("Generate Success.");
        return true;
    }
}
