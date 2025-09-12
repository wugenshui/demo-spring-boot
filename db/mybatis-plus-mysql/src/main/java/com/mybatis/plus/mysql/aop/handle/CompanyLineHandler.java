package com.mybatis.plus.mysql.aop.handle;

/**
 * @author : chenbo
 * @date : 2025-09-08
 */

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.schema.Column;

import java.util.List;

/**
 * 公司处理器（ Company 行级 ）
 *
 * @author hubin
 * @since 3.4.0
 */
public interface CompanyLineHandler {

    /**
     * 获取公司 ID 值表达式，只支持单个 ID 值
     * <p>
     *
     * @return 公司 ID 值表达式
     */
    Expression getCompanyId();

    /**
     * 获取字段名
     */
    default String getCompanyIdColumn() {
        return "tenant_id";
    }

    /**
     * 根据表名判断是否忽略拼接多公司条件
     * <p>
     * 默认都要进行解析并拼接多公司条件
     *
     * @param tableName 表名
     * @return 是否忽略, true:表示忽略，false:需要解析并拼接多公司条件
     */
    default boolean ignoreTable(String tableName) {
        return false;
    }

    /**
     * 忽略插入公司字段逻辑
     *
     * @param columns        插入字段
     * @param tenantIdColumn 公司 ID 字段
     * @return
     */
    default boolean ignoreInsert(List<Column> columns, String tenantIdColumn) {
        return columns.stream().map(Column::getColumnName).anyMatch(i -> i.equalsIgnoreCase(tenantIdColumn));
    }
}
