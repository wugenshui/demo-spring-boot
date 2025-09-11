package com.mybatis.plus.mysql.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author : chenbo
 * @date : 2025-09-11
 */
@SpringBootTest
class StudentServiceTest {

    @Resource
    private StudentService studentService;

    @Test
    void serviceList() {
        studentService.serviceList();
    }

    @Test
    void serviceListInterceptorIgnore() {
        studentService.serviceListInterceptorIgnore();
    }
}