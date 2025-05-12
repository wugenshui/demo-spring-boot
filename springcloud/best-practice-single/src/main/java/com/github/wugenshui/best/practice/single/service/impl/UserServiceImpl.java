package com.github.wugenshui.best.practice.single.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.wugenshui.best.practice.single.entity.User;
import com.github.wugenshui.best.practice.single.mapper.UserMapper;
import com.github.wugenshui.best.practice.single.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author chenbo
 * @since 2019-12-01
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public int resumeDelete(Long id) {
        return baseMapper.resumeDelete(id);
    }

    @Override
    public int resumeVersion(Long id) {
        return baseMapper.resumeVersion(id);
    }

    @Override
    public User getByName(String name) {
        return lambdaQuery().eq(User::getName, name).one();
    }

    @Override
    public boolean updateByName(String name, User user) {
        return lambdaUpdate().eq(User::getName, name).update(user);
    }
}
