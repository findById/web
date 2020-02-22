package com.cn.web.core.util;

public class NumberUtils {

    public static int toInteger(String input, int fallback) {
        try {
            return Integer.parseInt(input);
        } catch (Throwable e) {
            return fallback;
        }
    }

    public static long toLong(String input, long fallback) {
        try {
            return Long.parseLong(input);
        } catch (Throwable e) {
            return fallback;
        }
    }

    public static double toDouble(String input, double fallback) {
        try {
            return Double.parseDouble(input);
        } catch (Throwable e) {
            return fallback;
        }
    }

    public static float toFloat(String input, float fallback) {
        try {
            return Float.parseFloat(input);
        } catch (Throwable e) {
            return fallback;
        }
    }

    public static short toShort(String input, short fallback) {
        try {
            return Short.parseShort(input);
        } catch (Throwable e) {
            return fallback;
        }
    }
}
