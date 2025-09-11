package com.mybatis.plus.mysql;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.mybatis.plus.mysql.aop.DatabaseInterceptor;
import com.mybatis.plus.mysql.aop.handle.CompanyLineHandler;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Select;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author : chenbo
 * @date : 2025-09-09
 */
class CompanyIdTest {

    @ParameterizedTest(name = "{index} => sql=''{0}'', expected=''{1}''")
    @CsvSource({
            // 格式: 原始SQL, 期望结果SQL
            "SELECT * FROM user WHERE id = 1,                            SELECT * FROM user WHERE id = 1 AND company_id = 6114",
            "SELECT name FROM user,                                     SELECT name FROM user WHERE company_id = 6114",
            "SELECT * FROM user WHERE status = 'active',               SELECT * FROM user WHERE status = 'active' AND company_id = 6114",
            "SELECT * FROM user u,                                       SELECT * FROM user u WHERE u.company_id = 6114",
            "SELECT * FROM user WHERE id = 1 AND name = 'test',        SELECT * FROM user WHERE id = 1 AND name = 'test' AND company_id = 6114",
            "SELECT * FROM user WHERE id = 1 OR company_id = 2,        SELECT * FROM user WHERE (id = 1 OR company_id = 2) AND company_id = 6114",
            "SELECT * FROM user ORDER BY id DESC,                      SELECT * FROM user WHERE company_id = 6114 ORDER BY id DESC",
            "SELECT * FROM user LIMIT 10,                              SELECT * FROM user WHERE company_id = 6114 LIMIT 10",
            "SELECT * FROM user WHERE id IN (SELECT user_id FROM user_role), select * from user where id in (select user_id from user_role where company_id = 6114) and company_id = 6114"
    })

    @DisplayName("测试 SQL 重写：自动注入 company_id 条件")
    void testQuerySql(String inputSql, String expectedSql) throws JSQLParserException {
        // 模拟 CompanyLineHandler 返回固定的 company_id = 6114
        DatabaseInterceptor interceptor = new DatabaseInterceptor(new CompanyLineHandler() {
            @Override
            public Expression getCompanyId() {
                return new LongValue(6114);
            }
        });

        // 执行 SQL 处理
        String actualSql = interceptor.handleSql(inputSql);

        // 断言：忽略空格差异，进行标准化比较（可选）
        assertEquals(normalize(expectedSql), normalize(actualSql));
    }

    @ParameterizedTest(name = "{index} => {0}")
    @CsvSource(value = {
            // ===================== INSERT =====================
            "INSERT INTO user (id, name, company_id) VALUES (1, 'Tom', 1008) | INSERT INTO user (id, name, company_id) VALUES (1, 'Tom', 1008)",
            "INSERT INTO user(id, name) VALUES (1, 'Tom') | INSERT INTO user (id, name, company_id) VALUES (1, 'Tom', 6114)",
            "INSERT INTO user SELECT * FROM temp_user | INSERT INTO user SELECT * FROM temp_user",
            // 注：INSERT SELECT 不修改字段，需业务保证 source 表有 company_id

            // ===================== UPDATE =====================
            "UPDATE user SET name = 'Tom' | UPDATE user SET name = 'Tom' WHERE company_id = 6114",
            "UPDATE user SET name = 'Tom' WHERE status = 1 | UPDATE user SET name = 'Tom' WHERE status = 1 AND company_id = 6114",

            // ===================== DELETE =====================
            "DELETE FROM user | DELETE FROM user WHERE company_id = 6114",
            "DELETE FROM user WHERE status = 1 | DELETE FROM user WHERE status = 1 AND company_id = 6114"
    }, delimiter = '|')
    @DisplayName("测试各种 SQL 类型自动注入 company_id")
    void testOtherSql(String inputSql, String expectedSql) throws JSQLParserException {
        DatabaseInterceptor interceptor = new DatabaseInterceptor(new CompanyLineHandler() {
            @Override
            public Expression getCompanyId() {
                return new LongValue(6114);
            }
        });

        String actualSql = interceptor.handleSql(inputSql);
        assertEquals(normalize(expectedSql), normalize(actualSql));
    }

    /**
     * 简单标准化 SQL：去除多余空格，转小写（可按需调整）
     */
    private String normalize(String sql) {
        return sql.trim().replaceAll("\\s+", " ").toLowerCase();
    }

}
