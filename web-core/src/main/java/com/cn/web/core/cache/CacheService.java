package com.cn.web.core.cache;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public interface CacheService<T> {
    T put(Serializable key, T value);

    T put(Serializable key, T value, long timeout, TimeUnit unit);

    boolean remove(Serializable key);

    long removeKeys(String pattern);

    T get(Serializable key);

    T getAndSet(Serializable key, T value);

    Set<String> keys(String pattern);

    boolean exists(Serializable key);

    long expire(Serializable key, TimeUnit unit);

    boolean expire(Serializable key, long timeout, TimeUnit unit);

    boolean expireAt(Serializable key, Date date);

    long size();

    void clear();

    String flush();

    String status();
}
