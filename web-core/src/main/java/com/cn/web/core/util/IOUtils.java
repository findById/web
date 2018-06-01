package com.cn.web.core.util;

import java.io.*;
import java.net.URLConnection;
import java.util.Map;

public class IOUtils {

    public static long copyStream(InputStream is, OutputStream os) throws IOException {
        return copyStream(is, os, new byte[8196]);
    }

    public static long copyStream(InputStream is, OutputStream os, byte[] buffer) throws IOException {
        try {
            long total = 0;
            int len = 0;
            while ((len = is.read(buffer)) >= 0) {
                os.write(buffer, 0, len);
                total += len;
            }
            is.close();
            is = null;
            return total;
        } finally {
            closeQuietly(is, os);
        }
    }

    public static String asString(InputStream is, String charset) throws IOException {
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            copyStream(is, baos);
            return baos.toString(charset);
        } finally {
            closeQuietly(baos);
        }
    }

    public static byte[] asBytes(InputStream is) throws IOException {
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            copyStream(is, baos);
            return baos.toByteArray();
        } finally {
            closeQuietly(baos);
        }
    }

    public static void writer(String path, String content, boolean append) throws IOException {
        FileOutputStream fos = new FileOutputStream(path, append);
        OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
        BufferedWriter bw = new BufferedWriter(osw);
        bw.write(content);
        bw.flush();
        bw.close();
        osw.close();
    }

    /**
     * @see <a href="https://www.ietf.org/rfc/rfc1867.txt">RFC 1867</a>
     * @see <a href="https://www.ietf.org/rfc/rfc2046.txt">RFC 2046</a>
     */
    public static void writeFiles(String boundary, OutputStream os, Map<String, File> files, Map<String, String> fields) throws IOException {
        String NEW_LINE = "\r\n";
        String TWO_DASHES = "--";
        String BOUNDARY = boundary; //"*" + System.currentTimeMillis() + "*";
        // addRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

        DataOutputStream dos = new DataOutputStream(os);

        // writing header
        // dos.writeBytes("name" + ": " + "value" + NEW_LINE);

        // writing fields
        if (fields != null && !fields.isEmpty()) {
            for (Map.Entry<String, String> entry : fields.entrySet()) {
                dos.writeBytes(TWO_DASHES + BOUNDARY + NEW_LINE);
                dos.writeBytes("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + NEW_LINE);
                dos.writeBytes("Content-Type: text/plain; charset=UTF-8" + NEW_LINE);
                dos.writeBytes(NEW_LINE);
                dos.writeBytes(entry.getValue());
                dos.writeBytes(NEW_LINE);
            }
        }

        // writing files
        FileInputStream fis = null;
        for (Map.Entry<String, File> entry : files.entrySet()) {
            if (entry.getValue() == null || !entry.getValue().exists()) {
                continue;
            }

            dos.writeBytes(TWO_DASHES + BOUNDARY + NEW_LINE);
            dos.writeBytes("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"; filename=\"" + entry.getValue().getName() + "\"" + NEW_LINE);
            dos.writeBytes("Content-Type: " + URLConnection.guessContentTypeFromName(entry.getValue().getPath()) + NEW_LINE);
            dos.writeBytes("Content-Transfer-Encoding: binary" + NEW_LINE);
            dos.writeBytes(NEW_LINE);
            fis = new FileInputStream(entry.getValue());
            byte[] buffer = new byte[1024 * 10];
            int len = -1;
            while ((len = fis.read(buffer)) >= 0) {
                dos.write(buffer, 0, len);
            }
            dos.writeBytes(NEW_LINE);
            fis.close();
            fis = null;
        }
        dos.writeBytes(TWO_DASHES + BOUNDARY + TWO_DASHES + NEW_LINE);
        dos.flush();
        dos.close();
    }

    public synchronized static void closeQuietly(AutoCloseable... closeables) {
        if (closeables != null && closeables.length > 0) {
            for (AutoCloseable ac : closeables) {
                if (ac != null) {
                    try {
                        ac.close();
                        ac = null;
                    } catch (Exception e) {
                        // ignore
                    }
                }
            }
        }
    }
}
