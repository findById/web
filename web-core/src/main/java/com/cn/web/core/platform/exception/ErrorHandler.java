package com.cn.web.core.platform.exception;

import com.cn.web.core.platform.web.ResponseBuilder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.PrintWriter;
import java.io.StringWriter;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public String handler(Throwable ex) {
        ResponseBuilder.Builder builder = ResponseBuilder.newBuilder();
        builder.statusCode(500);
        builder.message(ex.getMessage());

        if (ex instanceof HandlerException) {
            builder.statusCode(((HandlerException) ex).getStatusCode());
        } else {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            builder.stackTrace(sw.toString());
        }

        return builder.buildJSONString();
    }

}
