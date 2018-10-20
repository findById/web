package com.cn.web.rbac.aspect;

import com.alibaba.fastjson.JSON;
import com.cn.web.rbac.annotation.AopLog;
import com.cn.web.rbac.util.IPUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.HashMap;

@Aspect
@Component
public class AnnLogAspect {
    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    @Pointcut("@annotation(com.cn.web.rbac.annotation.AopLog)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();

        Object result = point.proceed();

        saveLog(point, System.currentTimeMillis() - beginTime);

        return result;
    }

    @Before("pointcut()&&@annotation(ann)")
    public void before(JoinPoint joinPoint, AopLog ann) {
    }

    @After("pointcut()&&@annotation(ann)")
    public void after(JoinPoint joinPoint, AopLog ann) {
    }

    @AfterReturning(returning = "result", pointcut = "pointcut()")
    public void after(JoinPoint joinPoint, Object result) {
        saveResultLog(joinPoint, result);
    }

    @AfterThrowing(pointcut = "pointcut()", throwing = "throwable")
    public void after(Throwable throwable) {
        throwable.printStackTrace();
    }

    private void saveLog(ProceedingJoinPoint joinPoint, long costTime) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        StringBuilder sb = new StringBuilder();
        // access time
        sb.append('[').append(format.format(System.currentTimeMillis())).append(']');
        // address
        sb.append('[');
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        if (ra instanceof ServletRequestAttributes) {
            sb.append(IPUtils.getRemoteAddr(((ServletRequestAttributes) ra).getRequest()));
        }
        sb.append(']');
        // key
        AopLog ann = method.getAnnotation(AopLog.class);
        sb.append('[').append(ann != null ? ann.value() : "").append(']');
        // method
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        sb.append('[').append(className).append('.').append(methodName).append("()]");
        // parameter
        String[] names = signature.getParameterNames();
        Object[] args = joinPoint.getArgs();
        sb.append("[");
        if (names != null && names.length > 0 && args != null && args.length > 0) {
            HashMap<String, Object> param = new HashMap<>();
            int len = Math.min(names.length, args.length);
            for (int i = 0; i < len; i++) {
                Object obj = args[i];
                if (obj != null && ignoreParamValue(obj.getClass().getName())) {
                    param.put(names[i], obj.getClass().getName());
                    continue;
                }
                param.put(names[i], obj);
            }
            sb.append(JSON.toJSONString(param));
        }
        sb.append("]");
        // cost time
        sb.append('[').append(costTime).append("ms]");

        System.out.println(sb.toString());
    }

    private void saveResultLog(JoinPoint joinPoint, Object result) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        StringBuilder sb = new StringBuilder();
        // access time
        sb.append('[').append(format.format(System.currentTimeMillis())).append(']');
        // address
        sb.append('[');
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        if (ra instanceof ServletRequestAttributes) {
            sb.append(IPUtils.getRemoteAddr(((ServletRequestAttributes) ra).getRequest()));
        }
        sb.append(']');
        // key
        AopLog ann = method.getAnnotation(AopLog.class);
        sb.append('[').append(ann != null ? ann.value() : "").append(']');
        // method
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        sb.append('[').append(className).append('.').append(methodName).append("()]");
        // result
        sb.append("[").append(result).append("]");

        System.out.println(sb.toString());
    }

    private String[] ignoreList = new String[]{
            "org.springframework.web.multipart",
            "javax.servlet.http",
    };

    private boolean ignoreParamValue(String className) {
        for (String item : ignoreList) {
            if (className.contains(item)) {
                return true;
            }
        }
        return false;
    }
}
