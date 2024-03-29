package com.github.wugenshui.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author : chenbo
 * @date : 2020-05-24
 */
@SpringBootApplication
@EnableScheduling
public class RedisStarterApplication {
    public static void main(String[] args) {
        SpringApplication.run(RedisStarterApplication.class, args);
    }
}
