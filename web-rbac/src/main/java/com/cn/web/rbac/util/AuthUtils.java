package com.cn.web.rbac.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;

public class AuthUtils {
    private static final String ALGORITHM = "SHA-1";

    public static String randomPassword(int length) {
        String template = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_-.";
        Random random = new Random();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            result.append(template.charAt(random.nextInt(template.length()) % template.length()));
        }
        return result.toString();
    }

    public static String randomSalt() {
        return digest(UUID.randomUUID().toString(), ALGORITHM);
    }

    public static String encode(String password, String salt) {
        if (password == null) {
            return "";
        }
        if (salt == null) {
            salt = "";
        }
        return digest(password + "_SALT_" + salt, ALGORITHM);
    }

    private static String digest(String input, String algorithm) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance(algorithm);
            digest.reset();
            digest.update(input.getBytes());
            byte[] bytes = digest.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                if (Integer.toHexString(0xFF & b).length() == 1) {
                    sb.append("0").append(Integer.toHexString(0xFF & b));
                } else {
                    sb.append(Integer.toHexString(0xFF & b));
                }
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } finally {
        }
        return "";
    }

    public static boolean verify(String signature, String salt, String password) {
        return signature != null && !signature.isEmpty() && signature.equals(AuthUtils.encode(password, salt));
    }

}
