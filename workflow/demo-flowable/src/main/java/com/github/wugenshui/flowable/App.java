package com.github.wugenshui.flowable;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author : chenbo
 * @date : 2024-12-08
 */
@SpringBootApplication(proxyBeanMethods = false)
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
