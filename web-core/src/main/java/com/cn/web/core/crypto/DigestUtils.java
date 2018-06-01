package com.cn.web.core.crypto;

import com.cn.web.core.util.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DigestUtils {
    public static final String DEFAULT_CHARSET = "UTF-8";

    public static String md5(String text) {
        return encode(text, "MD5", DEFAULT_CHARSET);
    }

    public static String sha1(String text) {
        return encode(text, "SHA-1", DEFAULT_CHARSET);
    }

    public static String sha256(String text) {
        return encode(text, "SHA-256", DEFAULT_CHARSET);
    }

    public static String sha512(String text) {
        return encode(text, "SHA-512", DEFAULT_CHARSET);
    }

    public static String encode(String source, String algorithm, String charset) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.reset();
            md.update(source.getBytes(charset));
            byte[] buf = md.digest();
            return bytes2Hex(buf);
        } catch (Throwable e) {
        }
        return "";
    }

    public static String digestStream(InputStream is, String algorithm) {
        DigestInputStream dis = null;
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            dis = new DigestInputStream(is, digest);
            dis.on(true);
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = dis.read(buffer)) >= 0) {
                dis.read(buffer, 0, len);
            }
            byte[] bytes = digest.digest();
            return bytes2Hex(bytes);
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(dis);
        }
        return "";
    }

    public static String bytes2Hex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            if (Integer.toHexString(0xFF & bytes[i]).length() == 1) {
                sb.append("0").append(Integer.toHexString(0xFF & bytes[i]));
            } else {
                sb.append(Integer.toHexString(0xFF & bytes[i]));
            }
        }
        return sb.toString();
    }

}
