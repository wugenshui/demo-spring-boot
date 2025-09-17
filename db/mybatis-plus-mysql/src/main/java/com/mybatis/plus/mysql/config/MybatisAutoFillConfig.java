package com.mybatis.plus.mysql.config;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * MybatisPlus 自动填充配置类
 *
 * @author : chenbo
 * @date : 2020-12-26
 */
@Configuration
public class MybatisAutoFillConfig implements MetaObjectHandler {

    /**
     * 创建时间，注意这里设置的是类字段名称，不是数据库字段名称
     */
    private static final String CREATE_TIME = "createTime";

    /**
     * 创建人ID
     */
    private static final String CREATER = "creater";

    /**
     * 更新时间
     */
    private static final String UPDATE_TIME = "updateTime";

    /**
     * 更新人ID
     */
    private static final String UPDATER = "updater";

    @Override
    public void insertFill(MetaObject metaObject) {
        Date date = new Date();
        // 创建时间
        setFieldValByName(CREATE_TIME, date, metaObject);
        // 更新时间
        setFieldValByName(UPDATE_TIME, date, metaObject);
        // TODO 用户名
        String userName = "default";
        if (StrUtil.isNotEmpty(userName)) {
            setFieldValByName(CREATER, userName, metaObject);
            setFieldValByName(UPDATER, userName, metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Object fieldValByName = getFieldValByName(UPDATE_TIME, metaObject);
        if (fieldValByName == null) {
            setFieldValByName(UPDATE_TIME, new Date(), metaObject);
        }
        // TODO 用户名
        String userName = "default";
        if (StrUtil.isNotEmpty(userName)) {
            setFieldValByName(UPDATER, userName, metaObject);
        }
    }
}
