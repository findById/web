package com.cn.web.core.platform.web;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

public class ResponseBuilder {

    private final long beginTime = System.currentTimeMillis();
    private Map<String, Object> map = new HashMap<>(5);

    public static ResponseBuilder newBuilder() {
        return new ResponseBuilder();
    }

    private ResponseBuilder() {
    }

    public ResponseBuilder statusCode(Object statusCode) {
        this.map.put("statusCode", statusCode);
        return this;
    }

    public ResponseBuilder message(String message) {
        this.map.put("message", message);
        return this;
    }

    public ResponseBuilder stackTrace(Object stack) {
        this.map.put("stackTrace", String.valueOf(stack));
        return this;
    }

    public ResponseBuilder result(Object result) {
        this.map.put("result", result);
        return this;
    }

    public ResponseBuilder put(String key, Object value) {
        if (key == null) {
            key = "";
        }
        this.map.put(key, value);
        return this;
    }

    public Map<String, Object> build() {
        map.put("elapsedTime", System.currentTimeMillis() - beginTime);
        return map;
    }

    public String buildJSONString() {
        map.put("elapsedTime", System.currentTimeMillis() - beginTime);
        return JSON.toJSONString(map);
    }
}
