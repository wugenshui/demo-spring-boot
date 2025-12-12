package com.wugenshui.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "mcp.api")
public class McpApiKeyConfig {

    private List<String> validApiKeys;
}
