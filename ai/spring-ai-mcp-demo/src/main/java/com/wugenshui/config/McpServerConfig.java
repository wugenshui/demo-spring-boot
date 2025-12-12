package com.wugenshui.config;

import com.wugenshui.service.IExampleService;
import com.wugenshui.service.IStudentService;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class McpServerConfig {

    @Bean
    public ToolCallbackProvider exampleToolCallbackProvider(IExampleService exampleService) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(exampleService)
                .build();
    }

    @Bean
    public ToolCallbackProvider studentToolCallbackProvider(IStudentService studentService) {
        // 打印传入的 studentService 实例
        //System.out.println("studentService 实例: " + studentService.getClass().getName());
        return MethodToolCallbackProvider.builder()
                .toolObjects(studentService)
                .build();
    }

}