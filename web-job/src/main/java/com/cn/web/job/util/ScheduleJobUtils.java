package com.cn.web.job.util;

import com.cn.web.job.domain.ScheduleJob;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ScheduleJobUtils {
    private static final String TASK_PREFIX = "task_";
    public static final String KEY_JOB_ID = "id";
    public static final String KEY_JOB_GROUP = "group";
    public static final String KEY_JOB_NAME = "name";
    public static final String KEY_JOB_METHOD = "method";
    public static final String KEY_JOB_PARAMS = "params";

    private static TriggerKey getTriggerKey(ScheduleJob job) {
        return TriggerKey.triggerKey(String.format("%s%s", TASK_PREFIX, job.getId()), job.getGroup());
    }

    private static JobKey getJobKey(ScheduleJob job) {
        return JobKey.jobKey(String.format("%s%s", TASK_PREFIX, job.getId()), job.getGroup());
    }

    private static String getScheduleJobState(String state) {
        System.out.println("job state: " + state);
        switch (state) {
            case "NORMAL": {
                return ScheduleJob.JOB_STATE_RUNNING;
            }
            case "PAUSED": {
                return ScheduleJob.JOB_STATE_PAUSED;
            }
            default: {
                return ScheduleJob.JOB_STATE_STOPPED;
            }
        }
    }

    public static List<ScheduleJob> getScheduleJob(Scheduler scheduler) throws SchedulerException {
        GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
        Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
        List<ScheduleJob> jobList = new ArrayList<>();
        for (JobKey jobKey : jobKeys) {
            List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
            for (Trigger trigger : triggers) {
                ScheduleJob job = new ScheduleJob();
                job.setName(jobKey.getName());
                job.setGroup(jobKey.getGroup());
                job.setRemark("cron_" + trigger.getKey());
                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                job.setStatus(getScheduleJobState(triggerState.name()));
                if (trigger instanceof CronTrigger) {
                    CronTrigger cronTrigger = (CronTrigger) trigger;
                    String cronExpression = cronTrigger.getCronExpression();
                    job.setCron(cronExpression);
                }
                jobList.add(job);
            }
        }
        return jobList;
    }

    public static List<ScheduleJob> getRunningJob(Scheduler scheduler) throws SchedulerException {
        List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
        List<ScheduleJob> jobList = new ArrayList<>(executingJobs.size());
        for (JobExecutionContext executingJob : executingJobs) {
            ScheduleJob job = new ScheduleJob();
            JobDetail jobDetail = executingJob.getJobDetail();
            JobKey jobKey = jobDetail.getKey();
            Trigger trigger = executingJob.getTrigger();
            job.setName(jobKey.getName());
            job.setGroup(jobKey.getGroup());
            job.setRemark("cron_" + trigger.getKey());
            Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
            job.setStatus(getScheduleJobState(triggerState.name()));
            if (trigger instanceof CronTrigger) {
                CronTrigger cronTrigger = (CronTrigger) trigger;
                String cronExpression = cronTrigger.getCronExpression();
                job.setCron(cronExpression);
            }
            jobList.add(job);
        }
        return jobList;
    }

    public static Trigger getTrigger(Scheduler scheduler, ScheduleJob job) {
        try {
            return scheduler.getTrigger(getTriggerKey(job));
        } catch (SchedulerException e) {
            return null;
        }
    }

    public static void createScheduleJob(Scheduler scheduler, ScheduleJob job) {
        try {
            Class<? extends Job> jobClass = (Class<? extends Job>) (Class.forName(job.getMethod()).newInstance().getClass());
            JobDetail jobDetail = JobBuilder.newJob(jobClass)
                    .withIdentity(getJobKey(job))
                    .usingJobData(KEY_JOB_ID, String.valueOf(job.getId()))
                    .usingJobData(KEY_JOB_GROUP, job.getGroup())
                    .usingJobData(KEY_JOB_NAME, job.getName())
                    .usingJobData(KEY_JOB_METHOD, job.getMethod())
                    .usingJobData(KEY_JOB_PARAMS, job.getParams())
                    .build();
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(getTriggerKey(job))
                    .withSchedule(CronScheduleBuilder.cronSchedule(job.getCron()))
//                    .usingJobData(KEY_JOB_ID, String.valueOf(job.getId()))
//                    .usingJobData(KEY_JOB_GROUP, job.getGroup())
//                    .usingJobData(KEY_JOB_NAME, job.getName())
//                    .usingJobData(KEY_JOB_METHOD, job.getMethod())
//                    .usingJobData(KEY_JOB_PARAMS, job.getParams())
//                    .startAt(DateBuilder.futureDate(1, DateBuilder.IntervalUnit.SECOND))
//                    .startNow()
                    .build();
            scheduler.scheduleJob(jobDetail, trigger);

            if (!ScheduleJob.JOB_STATE_RUNNING.equals(job.getStatus())) {
                pauseScheduleJob(scheduler, job);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateScheduleJob(Scheduler scheduler, ScheduleJob job) throws SchedulerException {
        TriggerKey triggerKey = getTriggerKey(job);
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCron());
        trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
                .usingJobData(KEY_JOB_ID, String.valueOf(job.getId()))
                .usingJobData(KEY_JOB_GROUP, job.getGroup())
                .usingJobData(KEY_JOB_NAME, job.getName())
                .usingJobData(KEY_JOB_METHOD, job.getMethod())
                .usingJobData(KEY_JOB_PARAMS, job.getParams())
                .withSchedule(scheduleBuilder).build();
        scheduler.rescheduleJob(triggerKey, trigger);
    }

    public static void startScheduleJob(Scheduler scheduler, ScheduleJob job) throws SchedulerException {
        JobKey jobKey = getJobKey(job);
        scheduler.triggerJob(jobKey);
    }

    public static void resumeScheduleJob(Scheduler scheduler, ScheduleJob job) throws SchedulerException {
        JobKey jobKey = getJobKey(job);
        scheduler.resumeJob(jobKey);
    }

    public static void pauseScheduleJob(Scheduler scheduler, ScheduleJob job) throws SchedulerException {
        JobKey jobKey = getJobKey(job);
        scheduler.pauseJob(jobKey);
    }

    public static void deleteScheduleJob(Scheduler scheduler, ScheduleJob job) throws SchedulerException {
        JobKey jobKey = getJobKey(job);
        scheduler.pauseJob(jobKey);
        scheduler.unscheduleJob(getTriggerKey(job));
        scheduler.deleteJob(jobKey);
    }
}
