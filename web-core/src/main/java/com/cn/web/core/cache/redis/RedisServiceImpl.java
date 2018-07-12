package com.cn.web.core.cache.redis;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service("redisService")
public class RedisServiceImpl implements RedisService {

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
        return redisTemplate.delete(String.valueOf(key));
    }

    @Override
    public String get(Serializable key) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        return ops.get(String.valueOf(key));
    }

    @Override
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    @Override
    public boolean exists(Serializable key) {
        return redisTemplate.hasKey(String.valueOf(key));
    }

    @Override
    public void expire(Serializable key, long timeout, TimeUnit unit) {
        redisTemplate.expire(String.valueOf(key), timeout, unit);
    }

    @Override
    public long size() {
        Set<String> set = keys("*");
        return set == null ? 0 : set.size();
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
