package com.demo.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 *
 * @author : chenbo
 * @date : 2026-01-19
 */
@Component
@Slf4j
public class TopicListener {
    private final String TOPIC1 = "test-kerberos";

    @KafkaListener(topics = TOPIC1)
    public void processMessage(String content) {
        log.info("{}：接收到消息={}", TOPIC1, content);
        //throw new RuntimeException("测试异常，消费确认不通过，可以继续重新消费");
    }
}
