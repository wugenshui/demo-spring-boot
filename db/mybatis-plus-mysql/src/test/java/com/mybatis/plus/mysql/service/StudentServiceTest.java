package com.mybatis.plus.mysql.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author : chenbo
 * @date : 2025-09-11
 */
@SpringBootTest
class StudentServiceTest {

    @Resource
    private StudentService studentService;

    @Test
    void ignoreTenantWay1() {
        studentService.ignoreTenantWay1();
    }

    @Test
    void ignoreTenantWay2() {
        studentService.ignoreTenantWay2();
    }
}