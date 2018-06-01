package org.cn.web.generator.util;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DecimalFormat;

public class FileUtils {

    public static File createFile(String path) {
        File file = createParentFile(path);
        if (file != null && !file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return file;
    }

    public static File createFolder(String path) {
        File file = createParentFile(path);
        if (file != null && !file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    public static File createParentFile(String path) {
        try {
            File file = new File(path);
            File parent = new File(
                    file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf(File.separator)));
            if (!parent.exists()) {
                createParentFile(parent.getPath());
                parent.mkdirs();
            }
            return file;
        } catch (Exception ignored) {
        }
        return null;
    }

    public static String getFormatSize(long size) {
        try {
            DecimalFormat df = new DecimalFormat("##0.##");

            if (size >> 10 < 1) {
                return String.format("%sB", size);
            }
            if (size >> 20 < 1) {
                return String.format("%sKB", df.format(size / 1024.));
            }
            if (size >> 30 < 1) {
                return String.format("%sMB", df.format(size / 1024. / 1024));
            }
            if (size >> 40 < 1) {
                return String.format("%sGB", df.format(size / 1024. / 1024 / 1024));
            }
            if (size >> 50 < 1) {
                return String.format("%sTB", df.format(size / 1024. / 1024 / 1024 / 1024));
            }
            if (size >> 60 < 1) {
                return String.format("%sPB", df.format(size / 1024. / 1024 / 1024 / 1024 / 1024));
            }
            return String.format("%sEB", df.format(size / 1024. / 1024 / 1024 / 1024 / 1024 / 1024));
        } catch (Exception e) {
            return "";
        } finally {
        }
    }

    public synchronized static boolean deleteFile(File file) {
        if (!file.exists()) {
            return false;
        }
        if (file.isFile()) {
            file.delete();
        } else if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                deleteFile(f);
                if (f.isDirectory()) {
                    // 如果不删除文件夹 可不做此处理
                    f.delete();
                }
            }
            // 删除根目录
            file.delete();
        }
        return true;
    }

    private FileFilter filter = new FileFilter() {
        String[] FILE_TYPE = { "Thumbs.db", ".DS_Store" };

        @Override
        public boolean accept(File pathname) {
            for (int i = 0; i < FILE_TYPE.length; i++) {
                if (FILE_TYPE[i].equalsIgnoreCase(pathname.getName())) {
                    return false;
                }
            }
            return true;
        }
    };

    public void copyFile(File src, File dest) throws IOException {
        copyFile(src, dest, filter);
    }

    public void copyFile(File src, File dest, FileFilter filter) throws IOException {
        if (src.isFile()) {
            copyFileChannel(src, dest);
        } else if (src.isDirectory()) {
            if (!dest.exists()) {
                dest.mkdirs();
            } else if (!dest.isDirectory()) {
                throw new RuntimeException(dest.getAbsolutePath() + " is not a directory.");
            }
            File[] files = src.listFiles(filter);
            for (int i = 0; i < files.length; i++) {
                copyIterates(files[i], dest, filter);
            }
        }
    }

    private void copyIterates(File copyIn, File copyOut, FileFilter filter) throws IOException {
        if (copyIn.isFile()) {
            String newFile = copyOut.getAbsolutePath() + File.separator + copyIn.getName();
            copyFileChannel(copyIn, new File(newFile));
        } else if (copyIn.isDirectory()) {
            File newOut = new File(copyOut.getAbsoluteFile() + File.separator + copyIn.getName());
            newOut.mkdirs();
            File[] files = copyIn.listFiles(filter);
            for (int i = 0; i < files.length; i++) {
                copyIterates(files[i], newOut, filter);
            }
        }
    }

    public void copyFileChannel(File src, File target) throws IOException {
        try {
            Files.copy(Paths.get(URI.create(src.getPath())), Paths.get(URI.create(target.getPath())), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
