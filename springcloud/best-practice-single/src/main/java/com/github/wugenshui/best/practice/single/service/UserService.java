package com.github.wugenshui.best.practice.single.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.wugenshui.best.practice.single.entity.User;

/**
 * <p>
 * 用户 服务类
 * </p>
 *
 * @author chenbo
 * @since 2019-12-01
 */
public interface UserService extends IService<User> {
    /**
     * 恢复删除标记
     *
     * @param id 主键
     * @return 影响行数
     */
    int resumeDelete(Long id);


    /**
     * 版本恢复
     *
     * @param id 主键
     * @return 影响行数
     */
    int resumeVersion(Long id);

    /**
     * 根据用户名查找用户
     *
     * @param name 用户名
     * @return
     */
    User getByName(String name);


    /**
     * 根据用户名批量更新
     *
     * @param name 用户名
     * @param user 用户实体
     * @return
     */
    boolean updateByName(String name, User user);
}
