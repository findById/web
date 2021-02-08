package com.cn.web.core.platform.exception;

import com.cn.web.core.platform.web.Result;
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
        Result result = Result.error(500, ex.getMessage());
        if (ex instanceof HandlerException) {
            result.statusCode(((HandlerException) ex).getStatusCode());
        } else {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            result.stackTrace(sw.toString());
        }
        return result.toJSONString();
    }


    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String methodArgument(MethodArgumentNotValidException ex) {
        Result result = Result.error(400, ex.getMessage());
        BindingResult res = ex.getBindingResult();
        List<FieldError> fieldErrors = res.getFieldErrors();
        StringBuffer msg = new StringBuffer();
        fieldErrors.forEach(fieldError -> {
            msg.append("[").append(fieldError.getField()).append(",").append(fieldError.getDefaultMessage()).append("]");
        });
        result.message(msg.toString());
        return result.toJSONString();
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public String notFound(NoHandlerFoundException ex) {
        return Result.error(404, ex.getMessage()).toJSONString();
    }

}
