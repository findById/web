package com.cn.web.core.crypto;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class AESUtils {

    private static final int KEY_SIZE = 128;
    private static final String KEY_ALGORITHM = "AES";

    public static String initKey() {
        try {
            // 初始化密钥生成器，AES要求密钥长度为128位、192位、256位
            KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
            kg.init(KEY_SIZE);
            SecretKey secretKey = kg.generateKey();
            return bytes2Hex(secretKey.getEncoded());
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String encrypt(String arg0, String key) {
        try {
            KeyGenerator kGen = KeyGenerator.getInstance(KEY_ALGORITHM);
            SecureRandom sRnd = SecureRandom.getInstance("SHA1PRNG");
            sRnd.setSeed(key.getBytes(Charset.forName("UTF-8")));
            kGen.init(KEY_SIZE, sRnd);
            SecretKey secretKey = kGen.generateKey();
            SecretKeySpec sKeySpec = new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            byte[] byteContent = arg0.getBytes(Charset.forName("UTF-8"));
            cipher.init(Cipher.ENCRYPT_MODE, sKeySpec);
            byte[] result = cipher.doFinal(byteContent);
            return bytes2Hex(result);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String decrypt(String arg0, String key) {
        try {
            byte deStr[] = hex2Bytes(arg0);
            KeyGenerator kGen = KeyGenerator.getInstance(KEY_ALGORITHM);
            SecureRandom sRnd = SecureRandom.getInstance("SHA1PRNG");
            sRnd.setSeed(key.getBytes(Charset.forName("UTF-8")));
            kGen.init(KEY_SIZE, sRnd);
            SecretKey secretKey = kGen.generateKey();
            SecretKeySpec sKeySpec = new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, sKeySpec);
            byte[] result = cipher.doFinal(deStr);
            return new String(result, Charset.forName("UTF-8"));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
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
        return sb.toString().toUpperCase();
    }

    public static byte[] hex2Bytes(String hexStr) {
        if (hexStr.length() < 1) {
            return new byte[0];
        }
        int len = hexStr.length() / 2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }
}
