package com.cn.web.core.cache.memory;

import com.cn.web.core.cache.CacheService;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class MemoryCacheService implements CacheService<String> {

    private HashMap<String, String> cache = new HashMap<>();

    @Override
    public String put(Serializable key, String value) {
        cache.put(String.valueOf(key), value);
        return value;
    }

    @Override
    public String put(Serializable key, String value, long timeout, TimeUnit unit) {
        cache.put(String.valueOf(key), value);
        return value;
    }

    @Override
    public boolean remove(Serializable key) {
        cache.remove(String.valueOf(key));
        return true;
    }

    @Override
    public long removeKeys(String pattern) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String get(Serializable key) {
        return cache.get(String.valueOf(key));
    }

    @Override
    public String getAndSet(Serializable key, String value) {
        String res = cache.get(String.valueOf(key));
        cache.put(String.valueOf(key), value);
        return res;
    }

    @Override
    public Set<String> keys(String pattern) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean exists(Serializable key) {
        return cache.containsKey(String.valueOf(key));
    }

    @Override
    public long expire(Serializable key, TimeUnit unit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean expire(Serializable key, long timeout, TimeUnit unit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean expireAt(Serializable key, Date date) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long size() {
        return cache.size();
    }

    @Override
    public void clear() {
        cache.clear();
    }

    @Override
    public String flush() {
        return null;
    }

    @Override
    public String status() {
        return "connected";
    }
}
