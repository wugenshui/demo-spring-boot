package com.demo.listener;

import cn.hutool.core.util.StrUtil;
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
        handleMessage(content, 0);
    }

    /**
     * 消息分割与展示
     */
    public void handleMessage(String content, int wantedCount) {
        if (StrUtil.isNotEmpty(content)) {
            String[] splitMark = new String[]{"\u001B"};
            for (String mark : splitMark) {
                String[] splits = content.split(mark, -1);
                log.info("表字段数：{}，使用{}将文本拆分为{}块", wantedCount, mark, splits.length);
                for (int i = 0; i < splits.length; i++) {
                    log.info("第{}块：{}", i, splits[i]);
                }
            }
        }
    }
}
