package com.cn.web.core.cache.redis;

import java.io.Serializable;
import java.util.Set;

public interface RedisService {
    String put(Serializable key, String value);

    String put(Serializable key, String value, long liveTime);

    boolean remove(Serializable key);

    String get(Serializable key);

    Set<String> keys(String pattern);

    boolean exists(Serializable key);

    long size();

    void clear();

    String flush();

    boolean isConnected();

}
