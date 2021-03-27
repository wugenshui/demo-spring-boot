package com.chenbo.demo.single.best.practice.config;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author : chenbo
 * @date : 2021-03-27
 */
@Component
public class MinioBean {
    /**
     * minio client
     * @return
     */
    @Bean
    public MinioClient minioClient() {
        return MinioClient
                .builder()
                .endpoint("http://192.168.192.168:9000")
                .credentials("minio", "minio2021")
                .build();
    }
}
