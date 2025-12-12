package com.wugenshui.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class McpApiKeyInterceptor implements HandlerInterceptor {

    @Autowired
    private McpApiKeyConfig mcpApiKeyConfig;


    //拦截器
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authHeader = request.getHeader("API_KEY");
        log.info("请求头:{}", authHeader);
        if(authHeader != null && mcpApiKeyConfig.getValidApiKeys().contains(authHeader)){
            return true;
        }
        return false;
    }
}