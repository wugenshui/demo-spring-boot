package com.github.wugenshui.redis;

import com.github.wugenshui.redis.entiry.User;
import com.github.wugenshui.redis.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author : chenbo
 * @date : 2020-05-24
 */
@SpringBootTest
@Slf4j
class RedisUtilTest {
    @Resource
    private RedisUtil redisUtil;

    @Test
    void userTest() {
        String key = "user:0";
        // 单个实体 set\get
        User user = new User(0L, "aaa", "666666");
        redisUtil.set(key, user);
        User getUser = (User) redisUtil.get(key);
        System.out.println("getUser = " + getUser);
        Assertions.assertEquals(getUser.getPassword(), user.getPassword());

        // 设置值时设置过期时间(2分钟后过期)
        redisUtil.set("user:timeout", new User(20L, "czz", "xxxxxx"), 120);
    }

}
