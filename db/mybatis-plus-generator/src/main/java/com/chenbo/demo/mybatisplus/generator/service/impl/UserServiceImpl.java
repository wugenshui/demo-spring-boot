package com.chenbo.demo.mybatisplus.generator.service.impl;

import com.chenbo.demo.mybatisplus.generator.entity.User;
import com.chenbo.demo.mybatisplus.generator.mapper.UserMapper;
import com.chenbo.demo.mybatisplus.generator.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author chenbo
 * @since 2020-05-11
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
