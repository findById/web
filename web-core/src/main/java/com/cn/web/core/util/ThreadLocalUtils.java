package com.cn.web.core.util;

import java.util.HashMap;
import java.util.Map;

public class ThreadLocalUtils {

    private static final ThreadLocal<Map<String, Object>> local = ThreadLocal.withInitial(HashMap::new);

    public static Map<String, Object> getAll() {
        return new HashMap<>(local.get());
    }

    public static <T> T put(String key, T value) {
        local.get().put(key, value);
        return value;
    }

    public static Object remove(String key) {
        return local.get().remove(key);
    }

    public static <T> T get(String key) {
        // noinspection unchecked
        return (T) local.get().get(key);
    }

    public static <T> T getAndRemove(String key) {
        try {
            return get(key);
        } finally {
            remove(key);
        }
    }

}
