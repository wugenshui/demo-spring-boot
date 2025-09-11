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
    void way1() {
        studentService.way1();
    }

    @Test
    void way2() {
        studentService.way2();
    }
}