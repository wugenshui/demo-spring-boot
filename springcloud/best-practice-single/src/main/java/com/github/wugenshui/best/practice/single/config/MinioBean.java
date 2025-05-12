package com.github.wugenshui.best.practice.single.config;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 文件存储配置类
 *
 * @author : chenbo
 * @date : 2021-03-27
 */
@Component
public class MinioBean {
    /**
     * minio client
     *
     * @return
     */
    @Bean
    public MinioClient minioClient() {
        // 增加容错处理，防止没有配置时报错
        return MinioClient
                .builder()
                .endpoint("http://192.168.192.168:9000")
                .credentials("minio", "minio2021")
                .build();
    }
}
