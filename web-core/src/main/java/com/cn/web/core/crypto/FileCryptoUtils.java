package com.cn.web.core.crypto;

import java.io.*;

public class FileCryptoUtils {
    private static final int KEY = 0x99;

    public static void encode(File src, File dest) throws IOException {
        if (!src.exists()) {
            return;
        }
        if (!dest.exists()) {
            if (!dest.createNewFile()) {
                throw new IOException("can not create file: " + dest.getPath());
            }
        }
        InputStream is = new FileInputStream(src);
        OutputStream os = new FileOutputStream(dest);
        int position = 0;
        while ((position = is.read()) >= 0) {
            os.write(position ^ KEY);
        }

        is.close();
        os.flush();
        os.close();
    }

    public static void decode(File src, File dest) throws IOException {
        encode(src, dest);
    }
}
