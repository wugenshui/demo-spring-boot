package com.wugenshui.security.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * @author : chenbo
 * @date : 2025-05-06
 */
@Data
@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    /**
     * jwt密钥
     */
    private String jwtSecret;
    /**
     * jwt过期时间
     */
    private Duration jwtExpirationTime;
}
