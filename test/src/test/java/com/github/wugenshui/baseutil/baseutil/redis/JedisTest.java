package com.github.wugenshui.baseutil.baseutil.redis;

import com.github.fppt.jedismock.RedisServer;
import org.junit.Test;

import java.io.IOException;

/**
 * @author : chenbo
 * @date : 2025-11-06
 */
public class JedisTest {
    @Test
    public void test() throws InterruptedException {
        // 启动 Redis 服务
        RedisServer redisServer = new RedisServer(63791);
        try {
            redisServer.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Thread.sleep(300000000L);
    }
}
