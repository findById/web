package com.cn.web.core.platform.exception;

import com.cn.web.core.platform.web.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

@RestControllerAdvice
public class ErrorHandler {

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public String handler(Throwable ex) {
        ResponseBuilder builder = ResponseBuilder.newBuilder();
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


    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String methodArgument(MethodArgumentNotValidException ex) {
        ResponseBuilder builder = ResponseBuilder.newBuilder();
        builder.statusCode(400);
        builder.message(ex.getMessage());

        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        StringBuffer msg = new StringBuffer();
        fieldErrors.forEach(fieldError -> {
            msg.append("[").append(fieldError.getField()).append(",").append(fieldError.getDefaultMessage()).append("]");
        });
        builder.message(msg.toString());
        return builder.buildJSONString();
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public String notFound(NoHandlerFoundException ex) {
        ResponseBuilder builder = ResponseBuilder.newBuilder();
        builder.statusCode(404);
        builder.message(ex.getMessage());
        return builder.buildJSONString();
    }

}
