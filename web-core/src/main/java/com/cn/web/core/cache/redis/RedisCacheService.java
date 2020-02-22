package com.cn.web.core.cache.redis;

import com.alibaba.fastjson.JSON;
import com.cn.web.core.cache.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class RedisCacheService implements CacheService<String> {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Override
    public String put(Serializable key, String value) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set(String.valueOf(key), value == null ? "" : JSON.toJSONString(value));
        return value;
    }

    @Override
    public String put(Serializable key, String value, long timeout, TimeUnit unit) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set(String.valueOf(key), value == null ? "" : value, timeout, unit);
        return value;
    }

    @Override
    public boolean remove(Serializable key) {
        Boolean res = redisTemplate.delete(String.valueOf(key));
        return res != null && res;
    }

    @Override
    public long removeKeys(String pattern) {
        Long res = redisTemplate.delete(keys(pattern));
        return res != null ? res : 0L;
    }

    @Override
    public String get(Serializable key) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        return ops.get(String.valueOf(key));
    }

    @Override
    public String getAndSet(Serializable key, String value) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        return ops.getAndSet(String.valueOf(key), value);
    }

    @Override
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    @Override
    public boolean exists(Serializable key) {
        Boolean res = redisTemplate.hasKey(String.valueOf(key));
        return res != null && res;
    }

    @Override
    public long expire(Serializable key, TimeUnit unit) {
        Long res = redisTemplate.getExpire(String.valueOf(key), unit);
        return res != null ? res : 0L;
    }

    @Override
    public boolean expire(Serializable key, long timeout, TimeUnit unit) {
        Boolean res = redisTemplate.expire(String.valueOf(key), timeout, unit);
        return res != null && res;
    }

    @Override
    public boolean expireAt(Serializable key, Date date) {
        Boolean res = redisTemplate.expireAt(String.valueOf(key), date);
        return res != null && res;
    }

    @Override
    public long size() {
        Set<String> set = keys("*");
        return set == null ? 0 : set.size();
    }

    @Override
    public void clear() {
        redisTemplate.delete(keys("*"));
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
