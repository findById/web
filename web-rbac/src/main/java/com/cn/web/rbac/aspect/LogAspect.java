package com.cn.web.rbac.aspect;

import com.alibaba.fastjson.JSON;
import com.cn.web.rbac.annotation.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;

@Aspect
@Component
public class LogAspect {
    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    @Pointcut("@annotation(com.cn.web.rbac.annotation.Log)")
    public void logPointCut() {
    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();

        Object result = point.proceed();

        saveLog(point, System.currentTimeMillis() - beginTime);

        return result;
    }

    @Before("logPointCut()&&@annotation(ann)")
    public void before(JoinPoint joinPoint, Log ann) {
    }

    @After("logPointCut()&&@annotation(ann)")
    public void after(JoinPoint joinPoint, Log ann) {
    }

    private void saveLog(ProceedingJoinPoint joinPoint, long costTime) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        StringBuilder sb = new StringBuilder();
        sb.append('[').append(format.format(System.currentTimeMillis())).append(']'); // time

        Log ann = method.getAnnotation(Log.class);
        sb.append('[').append(ann != null ? ann.value() : "").append(']'); // key

        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        sb.append('[').append(className).append('.').append(methodName).append("()]"); // method

        Object[] args = joinPoint.getArgs();
        sb.append("[").append(JSON.toJSONString(args)).append("]"); // parameter

        sb.append('[').append(costTime).append("ms]"); // cost time

        System.out.println(sb.toString());
    }
}
