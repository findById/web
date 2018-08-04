package com.cn.web.job.util;

import com.cn.web.job.domain.ScheduleJob;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ScheduleJobUtils {

    public static void createScheduleJob(Scheduler scheduler, ScheduleJob job) {
        try {
            Class<? extends Job> jobClass = (Class<? extends Job>) (Class.forName(job.getMethod()).newInstance().getClass());
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(job.getName(), job.getGroup()).build();
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(job.getName(), job.getGroup())
                    .startAt(DateBuilder.futureDate(1, DateBuilder.IntervalUnit.SECOND))
                    .withSchedule(CronScheduleBuilder.cronSchedule(job.getCron()))
                    .usingJobData("group", job.getGroup())
                    .usingJobData("name", job.getName())
                    .usingJobData("method", job.getMethod())
                    .usingJobData("params", job.getParams())
                    .startNow()
                    .build();
            scheduler.scheduleJob(jobDetail, trigger);
            if (!scheduler.isShutdown()) {
                scheduler.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getScheduleJobState(String state) {
        System.out.println("job state: " + state);
        switch (state) {
            case "": {
                return ScheduleJob.JOB_STATE_STANDBY;
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

    public static void updateScheduleJob(Scheduler scheduler, ScheduleJob job) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(job.getName(), job.getGroup());
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCron());
        trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
        scheduler.rescheduleJob(triggerKey, trigger);
    }

    public static void startScheduleJob(Scheduler scheduler, ScheduleJob job) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(job.getName(), job.getGroup());
        scheduler.triggerJob(jobKey);
    }

    public static void resumeScheduleJob(Scheduler scheduler, ScheduleJob job) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(job.getName(), job.getGroup());
        scheduler.resumeJob(jobKey);
    }

    public static void pauseScheduleJob(Scheduler scheduler, ScheduleJob job) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(job.getName(), job.getGroup());
        scheduler.pauseJob(jobKey);
    }

    public static void deleteScheduleJob(Scheduler scheduler, ScheduleJob job) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(job.getName(), job.getGroup());
        scheduler.pauseJob(jobKey);
        scheduler.unscheduleJob(TriggerKey.triggerKey(job.getName(), job.getGroup()));
        scheduler.deleteJob(jobKey);
    }
}
