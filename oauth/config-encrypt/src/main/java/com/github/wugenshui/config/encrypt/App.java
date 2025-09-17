package com.github.wugenshui.config.encrypt;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * 配置文件加密说明
 * 1. 引入 jasypt-spring-boot-starter
 * 2. 在配置文件中配置加密密钥
 * 3. 在配置文件中通过ENC()写入加密后配置
 *
 * @author : chenbo
 * @date : 2022-11-25
 */
@SpringBootApplication
@EnableEncryptableProperties
public class App {
    public static void main(String[] args) {
        ConfigurableApplicationContext app = SpringApplication.run(App.class, args);
        ConfigurableEnvironment environment = app.getEnvironment();
        // 解密
        StringEncryptor encryptor = app.getBean(StringEncryptor.class);
        System.out.println("解密后：数据库 = " + environment.getProperty("spring.datasource.password"));
        System.out.println("解密后：缓存 = " + environment.getProperty("spring.redis.password"));

        // 加密
        System.out.println("加密后：数据库 = ENC(" + encryptor.encrypt("rVXkNMp52o") + ")");
        System.out.println("加密后：缓存 = ENC(" + encryptor.encrypt("WN$FFnmpk1") + ")");
    }
}
