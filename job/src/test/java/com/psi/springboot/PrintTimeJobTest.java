package com.psi.springboot;

import com.psi.springboot.Job.PrintTimeJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class PrintTimeJobTest {
    public static void main(String[] args) {
        //创建一个工具对象
        JobDetail jobDetail = JobBuilder.newJob(PrintTimeJob.class)
                .withIdentity("job1", "group1").build();
        //创建触发器Trigger,每2秒执行一次
        SimpleTrigger trigger = newTrigger()
                .withIdentity("trigger1", "group1").startNow()
                .withSchedule(simpleSchedule().withIntervalInSeconds(2)
                        .withRepeatCount(5)).build();
        //创建当前的调度工厂
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        //工厂生产调度器
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            scheduler.scheduleJob(jobDetail, trigger);
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
