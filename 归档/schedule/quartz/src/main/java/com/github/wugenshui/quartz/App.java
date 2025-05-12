package com.github.wugenshui.quartz;

import com.github.wugenshui.quartz.job.TestJob;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author : chenbo
 * @date : 2023-08-22
 */
public class App {
    public static void main(String[] args) throws SchedulerException {

        // 获取任务调度的实例
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        // 定义任务调度实例, 并与TestJob绑定
        JobDetail job = JobBuilder.newJob(TestJob.class)
                .usingJobData("data1", "jobDetail数据存放")
                .usingJobData("data2", "jobDetail数据存放2")
                .usingJobData("exeCount", 0)
                .withIdentity("testJob", "testJobGroup")
                .build();
        // 定义触发器, 会马上执行一次, 接着5秒执行一次
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("testTrigger", "testTriggerGroup")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(5))
                .build();
        // 使用触发器调度任务的执行
        scheduler.scheduleJob(job, trigger);
        // 开启任务
        scheduler.start();
    }
}
