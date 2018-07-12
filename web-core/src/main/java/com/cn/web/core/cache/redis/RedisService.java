package com.cn.web.core.cache.redis;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public interface RedisService {
    String put(Serializable key, String value);

    String put(Serializable key, String value, long timeout, TimeUnit unit);

    boolean remove(Serializable key);

    String get(Serializable key);

    Set<String> keys(String pattern);

    boolean exists(Serializable key);

    void expire(Serializable key, long timeout, TimeUnit unit);

    long size();

    void clear();

    String flush();

    boolean isConnected();

}
