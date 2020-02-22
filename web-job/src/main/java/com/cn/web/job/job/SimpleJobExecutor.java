package com.cn.web.job.job;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cn.web.core.platform.util.SpringContextUtils;
import com.cn.web.job.util.ScheduleJobUtils;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SimpleJobExecutor extends QuartzJobBean {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // SimpleThreadPool
    private static final ExecutorService executorService = Executors.newFixedThreadPool(10);

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        if (jobDataMap != null) {
//            System.out.println(JSON.toJSONString(jobDataMap));
//
//            System.out.println("id: " + jobDataMap.getString(ScheduleJobUtils.KEY_JOB_ID));
//            System.out.println("group: " + jobDataMap.getString(ScheduleJobUtils.KEY_JOB_GROUP));
//            System.out.println("name: " + jobDataMap.getString(ScheduleJobUtils.KEY_JOB_NAME));
//            System.out.println("method: " + jobDataMap.getString(ScheduleJobUtils.KEY_JOB_METHOD));
//            System.out.println("params: " + jobDataMap.getString(ScheduleJobUtils.KEY_JOB_PARAMS));

            String id = jobDataMap.getString(ScheduleJobUtils.KEY_JOB_ID);

            HashMap<String, String> data = new HashMap<>();
            data.put(ScheduleJobUtils.KEY_JOB_ID, jobDataMap.getString(ScheduleJobUtils.KEY_JOB_ID));
            data.put(ScheduleJobUtils.KEY_JOB_GROUP, jobDataMap.getString(ScheduleJobUtils.KEY_JOB_GROUP));
            data.put(ScheduleJobUtils.KEY_JOB_NAME, jobDataMap.getString(ScheduleJobUtils.KEY_JOB_NAME));
            data.put(ScheduleJobUtils.KEY_JOB_METHOD, jobDataMap.getString(ScheduleJobUtils.KEY_JOB_METHOD));
            data.put(ScheduleJobUtils.KEY_JOB_PARAMS, jobDataMap.getString(ScheduleJobUtils.KEY_JOB_PARAMS));

            long startTime = System.currentTimeMillis();
            try {
                // 执行任务
                logger.info(String.format("任务准备执行，任务ID：%s", id));
                Callable<Object> task = new JobTask(data);
                Future<?> future = executorService.submit(task);
                future.get();
                long time = System.currentTimeMillis() - startTime;
                logger.info(String.format("任务执行完毕，任务ID：%s 耗时：%s 毫秒", id, time));
            } catch (Throwable e) {
                long time = System.currentTimeMillis() - startTime;
                logger.error(String.format("任务执行失败，任务ID：%s 耗时：%s 毫秒", id, time), e);
            }
        }
    }

    private static class JobTask implements Callable<Object> {

        private Object worker;
        private Method method;
        private String params;

        public JobTask(HashMap<String, String> data) throws NoSuchMethodException {
            String temp = data.get(ScheduleJobUtils.KEY_JOB_PARAMS);
            JSONObject obj = JSON.parseObject(temp);
            String beanName = obj.getString("beanName");
            String methodName = obj.getString("methodName");
            params = obj.getString("params");

            worker = SpringContextUtils.getBean(beanName);
            if (worker == null) {
                throw new IllegalStateException("Can't found BeanName [" + beanName + "]");
            }

            if (params != null && !params.isEmpty()) {
                this.method = worker.getClass().getDeclaredMethod(methodName, String.class);
            } else {
                this.method = worker.getClass().getDeclaredMethod(methodName);
            }
        }

        @Override
        public Object call() throws Exception {
            Object result;
            method.setAccessible(true);
            if (params != null && !params.isEmpty()) {
                result = method.invoke(worker, params);
            } else {
                result = method.invoke(worker);
            }
            if (result instanceof Void) {
                return null;
            }
            return result;
        }
    }
}
