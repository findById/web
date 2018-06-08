package org.cn.web.generator.util;

import java.io.*;

public class GeneratorUtils {

    public static boolean createFile(String path) {
        /* --- createFile --- */
        File file = FileUtils.createParentFile(path);
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

    public static boolean createFolder(String path) {
        /* --- createFile --- */
        File file = FileUtils.createParentFile(path);
        if (!file.exists()) {
            file.mkdirs();
            return true;
        }
        return false;
    }

    /**
     * @param content
     * @param path
     */
    public static void writeFile(String content, String path) {
        try {
            if (GeneratorUtils.createFile(path)) {
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
}

