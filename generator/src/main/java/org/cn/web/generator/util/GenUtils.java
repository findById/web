package org.cn.web.generator.util;

import org.cn.web.generator.domain.Module;
import org.cn.web.generator.service.impl.GeneratorServiceImpl;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class GenUtils {
    public static final String TEMPLATE_WEB_CONTROLLER = "controller.ftl";
    public static final String TEMPLATE_WEB_REQ = "request.ftl";
    public static final String TEMPLATE_WEB_RESP = "response.ftl";
    public static final String TEMPLATE_WEB_VO = "bean.ftl";
    public static final String TEMPLATE_SERVICE = "service.ftl";
    public static final String TEMPLATE_SERVICE_IMPL = "serviceImpl.ftl";
    public static final String TEMPLATE_REPOSITORY = "repository.ftl";
    public static final String TEMPLATE_ENTITY = "entity.ftl";
    public static final String TEMPLATE_DAO = "dao.ftl";
    public static final String TEMPLATE_DAO_IMPL = "daoImpl.ftl";

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
                OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
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

    private static List<String> getTemplateList(String templateId) {
        List<String> list = new ArrayList<>();
        list.add(TEMPLATE_ENTITY);
        list.add(TEMPLATE_DAO);
        list.add(TEMPLATE_DAO_IMPL);
        list.add(TEMPLATE_REPOSITORY);
        list.add(TEMPLATE_SERVICE);
        list.add(TEMPLATE_SERVICE_IMPL);
        list.add(TEMPLATE_WEB_VO);
        list.add(TEMPLATE_WEB_REQ);
        list.add(TEMPLATE_WEB_RESP);
        list.add(TEMPLATE_WEB_CONTROLLER);
        return list;
    }

    private static String getFilePath(String template, String basePackage, String module, String clazz) {
        String javaPath = ("/src/main/java/" + basePackage).replaceAll("[\\/\\.]", "\\/");

        if (template.endsWith(TEMPLATE_ENTITY)) {
            return javaPath + "/" + module + "/domain/" + clazz + ".java";
        }
        if (template.endsWith(TEMPLATE_DAO)) {
            return javaPath + "/" + module + "/dao/" + clazz + "Dao.java";
        }
        if (template.endsWith(TEMPLATE_DAO_IMPL)) {
            return javaPath + "/" + module + "/dao/impl/" + clazz + "DaoImpl.java";
        }
        if (template.endsWith(TEMPLATE_REPOSITORY)) {
            return javaPath + "/" + module + "/repository/" + clazz + "Repository.java";
        }
        if (template.endsWith(TEMPLATE_SERVICE)) {
            return javaPath + "/" + module + "/service/" + clazz + "Service.java";
        }
        if (template.endsWith(TEMPLATE_SERVICE_IMPL)) {
            return javaPath + "/" + module + "/service/impl/" + clazz + "ServiceImpl.java";
        }
        if (template.endsWith(TEMPLATE_WEB_VO)) {
            return javaPath + "/" + module + "/web/vo/" + clazz + "Bean.java";
        }
        if (template.endsWith(TEMPLATE_WEB_REQ)) {
            return javaPath + "/" + module + "/web/request/" + clazz + "Req.java";
        }
        if (template.endsWith(TEMPLATE_WEB_RESP)) {
            return javaPath + "/" + module + "/web/response/" + clazz + "Resp.java";
        }
        if (template.endsWith(TEMPLATE_WEB_CONTROLLER)) {
            return javaPath + "/" + module + "/web/controller/" + clazz + "Controller.java";
        }
        return null;
    }

    public static byte[] genToZipFile(Module module) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);
        boolean result = generator(null, module, zos);
        try {
            zos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (result) {
            return baos.toByteArray();
        }
        return null;
    }

    public static boolean generator(File root, Module module) {
        return generator(root, module, null);
    }

    private static boolean generator(File root, Module module, ZipOutputStream zos) {
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
        while (root != null && (root.getPath().contains("/src/main") || root.getPath().contains("/src"))) {
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
        List<String> temp = getTemplateList("jpa");
        for (String t : temp) {
            String path = getFilePath(t, model.get("packageName").toString(), model.get("moduleName").toString(), model.get("ClassName").toString());
            System.out.println(t);
            String content = freeMarker.renderTemplate(t, model);
            if (content != null && path != null) {
                if (zos == null) {
                    writeFile(content, root + path);
                    System.out.println(">>" + root + path);
                } else {
                    try {
                        zos.putNextEntry(new ZipEntry(path));
                        zos.write(content.getBytes(StandardCharsets.UTF_8));
                        zos.closeEntry();
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println(e.getMessage() + " >> " + path);
                    }
                }
            }
        }
        System.out.println("Generate Success.");
        return true;
    }
}
