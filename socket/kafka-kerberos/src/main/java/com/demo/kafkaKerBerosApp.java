package com.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 调试需要添加虚拟机选项
 * -Djava.security.auth.login.config=D:/WorkSpace/demo-spring-boot/socket/kafka-kerberos/src/main/resources/kafka_client_jaas.conf -Djava.security.krb5.conf=D:/WorkSpace/demo-spring-boot/socket/kafka-kerberos/src/main/resources/krb5.conf
 *
 * @author : chenbo
 * @date : 2026-01-19
 */
@SpringBootApplication
public class kafkaKerberosApp {
    public static void main(String[] args) {
        SpringApplication.run(kafkaKerberosApp.class, args);
    }
}