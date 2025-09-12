package com.mybatis.plus.mysql.config;

import cn.hutool.core.lang.generator.SnowflakeGenerator;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.mybatis.plus.mysql.aop.DatabaseInterceptor;
import com.mybatis.plus.mysql.aop.MybatisTenantContext;
import com.mybatis.plus.mysql.aop.handle.CompanyLineHandler;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author : chenbo
 * @date : 2025-09-11
 */
@Configuration
public class MybatisPlusConfig {

    List<String> ignoreTableList = new ArrayList<>();

    @Bean
    public SnowflakeGenerator idGenerator() {
        return new SnowflakeGenerator();
    }

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // interceptor.addInnerInterceptor(dataPermissionDatabaseInterceptor());
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL)); // 如果配置多个插件, 切记分页最后添加
        // 如果有多数据源可以不配具体类型, 否则都建议配上具体的 DbType
        return interceptor;
    }

    /**
     * 公司权限拦截器
     */
    @Bean
    public DatabaseInterceptor dataPermissionDatabaseInterceptor() {
        return new DatabaseInterceptor(new CompanyLineHandler() {
            @Override
            public Expression getCompanyId() {
                return new LongValue(10086);
            }

            @Override
            public boolean ignoreTable(String tableName) {
                if (Boolean.TRUE.equals(MybatisTenantContext.get())) {
                    return true;
                }
                tableName = tableName.replaceAll("`", "").toLowerCase();
                return tableName.startsWith("wb_") || ignoreTableList.contains(tableName);
            }
        });
    }
}
