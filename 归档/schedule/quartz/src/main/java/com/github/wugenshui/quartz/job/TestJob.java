package com.github.wugenshui.quartz.job;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.PersistJobDataAfterExecution;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 自定义任务
 *
 * @author : chenbo
 * @date : 2023-08-22
 */
@PersistJobDataAfterExecution
public class TestJob implements Job {
    private Integer exeCount;

    public void setExeCount(Integer exeCount) {
        this.exeCount = exeCount;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        // 获取任务详情
        JobDetail jobDetail = jobExecutionContext.getJobDetail();
        String name = jobDetail.getKey().getName();
        String group = jobDetail.getKey().getGroup();
        String jobName = jobDetail.getJobClass().getName();
        System.out.println("jobName = " + jobName + ", name = " + name + ", group = " + group);

        // 获取任务参数
        String data1 = jobDetail.getJobDataMap().getString("data1");
        String data2 = jobDetail.getJobDataMap().getString("data2");
        System.out.println("data1 = " + data1 + ", data2 = " + data2);

        // 定时任务具体执行逻辑
        String data = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println("开启数据备份，当前次数：" + ++exeCount + " 当前时间：" + data);

        // 将累加的 count 存入JobDataMap中
        jobExecutionContext.getJobDetail().getJobDataMap().put("exeCount", exeCount);
    }
}