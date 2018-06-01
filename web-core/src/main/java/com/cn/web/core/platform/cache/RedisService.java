package com.cn.web.core.platform.cache;

import java.io.Serializable;
import java.util.Set;

public interface RedisService {
    Object put(Serializable key, Object value);

    Object put(Serializable key, Object value, long liveTime);

    Object remove(Serializable key);

    Object get(Serializable key);

    Set keys(String key);

    boolean exists(Serializable key);

    long size();

    void clear();

    String flush();

    boolean isConnected();

}
