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

    /// **
    // * 默认元对象处理器 Bean
    // *
    // * @return 默认元对象处理器
    // */
    //@Bean
    //public MetaObjectHandler defaultMetaObjectHandler() {
    //    return new DefaultDBFieldHandler(); // 自动填充参数类
    //}
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL)); // 如果配置多个插件, 切记分页最后添加
        // 如果有多数据源可以不配具体类型, 否则都建议配上具体的 DbType
        return interceptor;
    }

    @Bean
    public DatabaseInterceptor dataPermissionDatabaseInterceptor(MybatisPlusInterceptor interceptor) {

        // 创建 DataPermissionDatabaseInterceptor 拦截器
        DatabaseInterceptor inner = new DatabaseInterceptor(new CompanyLineHandler() {
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
        List<InnerInterceptor> inners = new ArrayList<>(interceptor.getInterceptors());
        inners.add(0, inner);
        interceptor.setInterceptors(inners);
        return inner;
    }
}
