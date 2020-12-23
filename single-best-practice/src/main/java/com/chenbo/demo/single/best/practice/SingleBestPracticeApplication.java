package com.chenbo.demo.single.best.practice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author chenbo
 * @date 2020-12-23
 */
@SpringBootApplication
@MapperScan("com.chenbo.demo.single.best.practice.**.mapper")
public class SingleBestPracticeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SingleBestPracticeApplication.class, args);
    }

}
