package com.github.wugenshui.aop.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *  Aop 测试，注解拦截可以进行权限或其他控制
 * @author : chenbo
 * @date : 2023-11-13
 */
@SpringBootApplication
public class AopTestApp {
    public static void main(String[] args) {
        SpringApplication.run(AopTestApp.class, args);
    }
}
