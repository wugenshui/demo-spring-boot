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
        if (StrUtil.isEmpty(content)) {
            return;
        }

        String mark = "\u001B";
        String[] splits = content.split(mark, -1);

        log.info("表字段数：{}，使用 {} 将文本拆分为 {} 块", wantedCount, mark, splits.length);

        // 每行最多打印 10 个字段
        int batchSize = 10;
        for (int start = 0; start < splits.length; start += batchSize) {
            int end = Math.min(start + batchSize, splits.length);
            StringBuilder line = new StringBuilder();
            for (int i = start; i < end; i++) {
                String val = splits[i];
                // 将 "/N" 转为空字符串
                if ("\\N".equals(val)) {
                    val = "";
                } else {
                    // 可选：对特殊字符或长内容做安全截断（如需要）
                    // val = StrUtil.maxLength(val, 30);
                    line.append("[").append(i + 1).append("]=").append(val).append(", ");
                }
            }
            // 去掉末尾多余的 ", "
            if (line.length() > 2) {
                line.setLength(line.length() - 2);
            }
            log.info("字段 {} ～ {}：{}", start + 1, end, line);
        }
    }
}
