package com.github.wugenshui.single.best.practice.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.wugenshui.best.practice.single.entity.User;
import com.github.wugenshui.best.practice.single.mapper.UserMapper;
import com.github.wugenshui.best.practice.single.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author : chenbo
 * @date : 2020-12-25
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {
    @Resource
    private UserService userService;

    @Resource
    private UserMapper userMapper;

    @Test
    public void queryTest() {
        List<User> list = userService.list();
        System.out.println("list = " + list);
        Assert.assertNotNull(list);

        User user = userService.getById(list.get(0).getId());
        System.out.println("user = " + user);

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.le(User::getCreateTime, new Date());
        List<User> users = userMapper.customQueryList(queryWrapper);
        System.out.println("users = " + users);

        User one = userService.getByName("王天风");
        System.out.println("one = " + one);
    }
}
