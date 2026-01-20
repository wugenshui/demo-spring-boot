package com.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.net.URL;

/**
 * 运行前需添加虚拟机选项：
 * -Djava.security.auth.login.config=D:/WorkSpace/demo-spring-boot/socket/kafka-kerberos/src/main/resources/jaas.conf -Djava.security.krb5.conf=D:/WorkSpace/demo-spring-boot/socket/kafka-kerberos/src/main/resources/krb5.conf
 *
 * @author : chenbo
 * @date : 2026-01-19
 */
@SpringBootApplication
public class kafkaKerberosApp {
    public static void main(String[] args) {
        // 在 Spring Boot 启动前设置 Kerberos 相关系统属性
        // 这样可以确保 Kafka 客户端在初始化时能够正确加载 JAAS 配置和 krb5.conf
        // 参考 KafkaDome 项目的实现方式，在代码中显式设置系统属性

        // 如果 JVM 参数已经设置，则使用 JVM 参数；否则尝试从 classpath 或项目路径加载
        if (System.getProperty("java.security.auth.login.config") == null) {
            String jaasPath = findConfigFile("jaas.conf");
            if (jaasPath != null) {
                System.setProperty("java.security.auth.login.config", jaasPath);
                System.out.println("设置 JAAS 配置路径: " + jaasPath);
            }
        }

        if (System.getProperty("java.security.krb5.conf") == null) {
            String krb5Path = findConfigFile("krb5.conf");
            if (krb5Path != null) {
                System.setProperty("java.security.krb5.conf", krb5Path);
                System.out.println("设置 krb5.conf 路径: " + krb5Path);
            }
        }

        SpringApplication.run(kafkaKerberosApp.class, args);
    }

    /**
     * 查找配置文件，优先从 classpath 查找，如果找不到则从项目路径查找
     */
    private static String findConfigFile(String fileName) {
        // 首先尝试从 classpath 加载（打包后的环境）
        URL resource = kafkaKerberosApp.class.getClassLoader().getResource(fileName);
        if (resource != null) {
            try {
                File file = new File(resource.toURI());
                if (file.exists()) {
                    return file.getAbsolutePath();
                }
            } catch (Exception e) {
                // 忽略异常，继续尝试其他方式
            }
        }

        // 如果 classpath 找不到，尝试从项目路径查找（开发环境）
        String basePath = System.getProperty("user.dir");
        String configPath = basePath + File.separator + "config" + File.separator + fileName;
        File file = new File(configPath);
        if (file.exists()) {
            return file.getAbsolutePath();
        }

        return null;
    }
}