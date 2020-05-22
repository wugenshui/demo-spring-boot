package com.chenbo.baseutil.java.util;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author : chenbo
 * @date : 2020-05-22
 */
@SpringBootTest
public class DelayQueueTest {
    @Test
    public void queueTest() throws InterruptedException {
        Item item1 = new Item("张三", 5, TimeUnit.SECONDS);
        Item item2 = new Item("李四", 10, TimeUnit.SECONDS);
        Item item3 = new Item("王五", 15, TimeUnit.SECONDS);
        DelayQueue<Item> queue = new DelayQueue<>();
        queue.put(item1);
        queue.put(item2);
        queue.put(item3);
        System.out.println("begin time:" + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        for (int i = 0; i < 3; i++) {
            Item take = queue.take();
            System.out.format("name:{%s}, time:{%s}\n", take.name, LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        }
    }

    class Item implements Delayed {
        /* 触发时间*/
        private long time;
        String name;

        public Item(String name, long time, TimeUnit unit) {
            this.name = name;
            this.time = System.currentTimeMillis() + (time > 0 ? unit.toMillis(time) : 0);
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return time - System.currentTimeMillis();
        }

        @Override
        public int compareTo(Delayed o) {
            Item item = (Item) o;
            long diff = this.time - item.time;
            // 改成>=会造成问题
            if (diff <= 0) {
                return -1;
            } else {
                return 1;
            }
        }

        @Override
        public String toString() {
            return "Item{" +
                    "time=" + time +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
