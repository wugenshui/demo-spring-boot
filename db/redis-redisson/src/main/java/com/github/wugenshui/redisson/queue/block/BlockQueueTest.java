package com.github.wugenshui.redisson.queue.block;

import com.github.wugenshui.redisson.queue.User;
import lombok.SneakyThrows;
import org.redisson.Redisson;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Scanner;

/**
 * 阻塞队列
 *
 * @author : chenbo
 * @date : 2020-06-01
 */
@Component
public class BlockQueueTest {

    private static final String QUEUE_NAME = "anyQueue";
    private static final String REDIS_ADDRESS = "redis://localhost:6379";

    public static void main(String[] args) {
        BlockQueueTest.receiver();
        BlockQueueTest.sender();
    }

    /**
     * 消息接收者
     */
    public static void receiver() {
        new Thread() {
            @SneakyThrows
            @Override
            public void run() {
                Config config = new Config();
                // 单机模式
                config.useSingleServer().setAddress(REDIS_ADDRESS);

                RedissonClient redisson = Redisson.create(config);

                RBlockingQueue<User> queue = redisson.getBlockingQueue(QUEUE_NAME);
                //queue.offer(User.builder().name("张三").age(12).birthday(LocalDateTime.now()).build());
                User obj = queue.peek();
                System.out.println("obj = " + obj);
                //User someObj = queue.poll();
                //User ob = queue.poll(10, TimeUnit.MINUTES);
            }
        }.start();
    }

    /**
     * 消息发送者
     */
    public static void sender() {
        Config config = new Config();
        // 单机模式
        config.useSingleServer().setAddress(REDIS_ADDRESS);

        RedissonClient redisson = Redisson.create(config);

        RBlockingQueue<User> queue = redisson.getBlockingQueue(QUEUE_NAME);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("请输入用户姓名:");
            String name = scanner.nextLine();
            queue.offer(User.builder().name(name).age(12).birthday(LocalDateTime.now()).build());
        }
    }
}
