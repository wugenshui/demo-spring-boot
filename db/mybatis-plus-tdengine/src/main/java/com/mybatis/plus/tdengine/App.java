package com.mybatis.plus.tdengine;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author : chenbo
 * @date : 2021-12-17
 */
@SpringBootApplication
@MapperScan("com.mybatis.plus.tdengine.**.mapper")
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
