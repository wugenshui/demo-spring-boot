package com.github.wugenshui.baseutil.baseutil.hutool;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.StopWatch;
import cn.hutool.core.lang.Console;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author : chenbo
 * @date : 2020-09-28
 */
@SpringBootTest
public class DateUtilTest {
    @Test
    public void apiTest() {
        Date date = new Date();

        System.out.println(DateUtil.beginOfYear(date) + " 至 " + DateUtil.endOfYear(date));
        System.out.println(DateUtil.beginOfMonth(date) + " 至 " + DateUtil.endOfMonth(date));
        System.out.println(DateUtil.beginOfWeek(date) + " 至 " + DateUtil.endOfWeek(date));
        System.out.println(DateUtil.beginOfDay(date) + " 至 " + DateUtil.endOfDay(date));

        System.out.println("当前时间 = " + DateUtil.now());
        System.out.println("当天 = " + DateUtil.today());
        System.out.println("时间戳 = " + DateUtil.date().getTime() + " : " + DateUtil.current());
        System.out.println("时间转换 = " + DateUtil.date(Long.parseLong(String.valueOf(DateUtil.current()))));

        System.out.println(DateUtil.format(date, DatePattern.NORM_DATETIME_FORMAT));
        System.out.println(DateUtil.format(date, DatePattern.NORM_DATE_FORMAT));

        DateTime tomorrow = DateUtil.offsetDay(date, 1);
        System.out.println("tomorrow = " + tomorrow);
        System.out.println(DateUtil.between(date, tomorrow, DateUnit.HOUR));
        System.out.println(DateUtil.between(tomorrow, date, DateUnit.HOUR));

        // 月数计算
        long betweenMonth = DateUtil.betweenMonth(DateUtil.parse("2024-01-01"), DateUtil.parse("2039-02-01"), false);
        System.out.println("betweenMonth = " + betweenMonth);
    }

    @Test
    public void stopWatchTest() throws InterruptedException {
        StopWatch stopWatch = DateUtil.createStopWatch();

        // 任务1
        stopWatch.start("任务一");
        Thread.sleep(1000);
        stopWatch.stop();

        // 任务2
        stopWatch.start("任务二");
        Thread.sleep(2000);
        stopWatch.stop();

        // 打印出耗时
        Console.log(stopWatch.prettyPrint(TimeUnit.MILLISECONDS));
    }

    @Test
    public void formatTest() {
        System.out.println(DateUtil.formatBetween(DateUtil.yesterday(), DateUtil.date()));
        System.out.println(DateUtil.formatBetween(DateUtil.lastWeek(), DateUtil.date()));
        System.out.println(DateUtil.formatBetween(DateUtil.lastMonth(), DateUtil.date()));
        System.out.println(DateUtil.formatBetween(DateUtil.beginOfYear(DateUtil.date()), DateUtil.date()));
    }
}
