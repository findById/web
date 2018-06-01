package com.cn.web.core.platform.cache;

import java.io.Serializable;
import java.util.Set;

//@Service("redisService")
public class RedisServiceImpl implements RedisService {
    @Override
    public Object put(Serializable key, Object value) {
        return null;
    }

    @Override
    public Object put(Serializable key, Object value, long liveTime) {
        return null;
    }

    @Override
    public Object remove(Serializable key) {
        return null;
    }

    @Override
    public Object get(Serializable key) {
        return null;
    }

    @Override
    public Set keys(String key) {
        return null;
    }

    @Override
    public boolean exists(Serializable key) {
        return false;
    }

    @Override
    public long size() {
        return 0;
    }

    @Override
    public void clear() {

    }

    @Override
    public String flush() {
        return null;
    }

    @Override
    public boolean isConnected() {
        return false;
    }
}
