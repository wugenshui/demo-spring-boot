package com.mybatis.plus.mysql.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.BadSqlGrammarException;

import javax.annotation.Resource;

/**
 * @author : chenbo
 * @date : 2025-09-11
 */
@SpringBootTest
class StudentMapperTest {

    @Resource
    private StudentMapper studentMapper;

    @Test
    void list() {
        Assertions.assertThrows(BadSqlGrammarException.class, () -> studentMapper.list());
    }

    @Test
    void listInterceptorIgnore() {
        System.out.println(studentMapper.listInterceptorIgnore());
    }
}