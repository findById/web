package com.cn.web.core.platform.exception;

public class HandlerException extends RuntimeException {
    private final Object statusCode;

    public HandlerException(Object statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public HandlerException(Object statusCode, String message, Throwable cause) {
        super(message, cause);
        this.statusCode = statusCode;
    }

    public Object getStatusCode() {
        return statusCode;
    }
}
