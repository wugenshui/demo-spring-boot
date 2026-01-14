package com.demo.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.demo.util.SseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * EventStream 接口
 *
 * @author : chenbo
 * @date : 2024-06-03
 */
@Controller
@RequestMapping
@Slf4j
public class AIController {

    /**
     * 大模型
     */
    @PostMapping(value = "/proxy")
    public SseEmitter proxy() {
        JSONObject body = JSONUtil.createObj()
                .set("model", "Qwen3-30B-A3B")
                .set("stream", true)
                .set("messages", List.of(Map.of("role", "user", "content", "你是")));

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", "sk-");
        headers.put("Content-Type", "application/json;charset=UTF-8");

        return SseUtil.proxySseStream("http://nginx.llm.test.com:30000/api/qwen3/v1/chat/completions", body, headers);
    }


    /**
     * 智能体应用
     */
    @PostMapping(value = "/proxy1")
    public SseEmitter proxy1() {
        JSONObject body = JSONUtil.createObj()
                .set("appId", "613564319623282688")
                .set("stream", true)
                .set("chatId", "001")
                .set("caller", "test")
                .set("replyOrigin", 1)
                .set("messages", List.of(Map.of("role", "user", "content", "你是")));

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", "hwllm-611026796183289856ldNl3ha");
        headers.put("Content-Type", "application/json;charset=UTF-8");

        return SseUtil.proxySseStream("https://ai.test.com/api/chat/api/v1/chat/completions", body, headers);
    }

    // 测试流式输出： curl -X POST http://localhost:8080/stream
    @PostMapping(value = "/proxy2")
    public SseEmitter proxy2() throws IOException {
        // 超时时间设置为3s，用于演示客户端自动重连
        SseEmitter emitter = new SseEmitter(30000L);
        // 设置前端的重试时间为1s
        emitter.send(SseEmitter.event().reconnectTime(1000).data("连接成功"));
        emitter.send(SseEmitter.event().data("立即发送第一条消息"));
        new Thread(() -> {
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                emitter.send(SseEmitter.event().name("msg").data("延时发送第二条消息"));
                emitter.complete();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();

        emitter.onTimeout(() -> {
            System.out.println("超时");
        });
        emitter.onCompletion(() -> System.out.println("完成！！！"));
        return emitter;
    }

}
