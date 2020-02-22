package com.cn.web.core.util;

import java.text.SimpleDateFormat;

public class DateUtils {

    private DateUtils() {
    }

    public static String formatTimestamp(long timestamp, String pattern) {
        return new SimpleDateFormat(pattern).format(timestamp);
    }

    public static String formatTimestamp(long timestamp) {
        return formatTimestamp(timestamp, "yyyy-MM-dd HH:mm:ss");
    }

    public static String currentTime(String pattern) {
        return formatTimestamp(System.currentTimeMillis(), pattern);
    }

    public static String currentTime() {
        return formatTimestamp(System.currentTimeMillis());
    }

}
