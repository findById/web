package com.cn.web.core.platform.web;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Result implements Serializable {
    public static final String KEY_REQUEST_START_TIME = "REQUEST-START-TIME";

    private final Map<String, Object> map = new HashMap<>(6);

    public Result statusCode(Object statusCode) {
        this.map.put("statusCode", statusCode);
        return this;
    }

    public Result message(String message) {
        this.map.put("message", message);
        return this;
    }

    public Result stackTrace(Object stack) {
        this.map.put("stackTrace", String.valueOf(stack));
        return this;
    }

    public Result result(Object result) {
        this.map.put("data", result);
        return this;
    }

    public Result put(String key, Object value) {
        if (key == null) {
            key = "";
        }
        this.map.put(key, value);
        return this;
    }

    public static Result success() {
        Result result = new Result();
        result.statusCode(200);
        result.message("success");
        return result;
    }

    public static Result success(Object data) {
        Result result = new Result();
        result.statusCode(200);
        result.message("success");
        result.result(data);
        return result;
    }

    public static Result error(Object statusCode, String message) {
        Result result = new Result();
        result.statusCode(statusCode);
        result.message(message);
        return result;
    }

    public static Result error(Object statusCode, String message, Object stack) {
        Result result = new Result();
        result.statusCode(statusCode);
        result.message(message);
        result.stackTrace(stack);
        return result;
    }

    public String toJSONString() {
        return toJSONString(0L);
    }

    public String toJSONString(Long beginTime) {
        if (map.get("data") == null) {
            result(new HashMap<String, Object>(1));
        }
        long timestamp = System.currentTimeMillis();
        map.put("timestamp", timestamp);
        if (beginTime > 0) {
            map.put("elapsedTime", timestamp - beginTime);
        }
        return JSON.toJSONString(map);
    }
}
