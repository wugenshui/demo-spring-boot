package com.wugenshui.config;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Resource
    private McpApiKeyInterceptor mcpApiKeyInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 指定拦截MCP服务器的SSE端点路径
        //registry.addInterceptor(mcpApiKeyInterceptor).addPathPatterns("/mcp/**");
    }
}