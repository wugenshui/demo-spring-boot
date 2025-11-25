package com.demo.ai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : chenbo
 * @date : 2025-11-25
 */
@Configuration
public class ChatClientConfig {
    @Autowired
    private ToolCallbackProvider tools;
    @Autowired
    OpenAiChatModel chatModel;

    @Bean
    public CommandLineRunner predefinedQuestions(
            ConfigurableApplicationContext context) {
        return args -> {
            // 构建ChatClient,此时不注入任何工具
            var chatClient = ChatClient.builder(chatModel)
                    .build();
            String userInput = "你是谁"; // 帮我将这个网页内容进行抓取 https://www.shuaijiao.cn/news/view/68320.html
            System.out.println("\n>>> QUESTION: " + userInput);
            System.out.println("\n>>> ASSISTANT: " + chatClient.prompt().user(userInput).call().content());

            context.close();
        };
    }
}
