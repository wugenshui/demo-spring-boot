package com.github.wugenshui.mybatisplus.generator.service.impl;

import com.github.wugenshui.mybatisplus.generator.entity.User;
import com.github.wugenshui.mybatisplus.generator.mapper.UserMapper;
import com.github.wugenshui.mybatisplus.generator.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author chenbo
 * @since 2020-05-12
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
