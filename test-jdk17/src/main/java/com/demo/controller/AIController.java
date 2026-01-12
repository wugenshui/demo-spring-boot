package com.demo.controller;

import com.demo.util.SseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

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

    // 测试流式输出： curl -X POST http://localhost:8080/stream
    @PostMapping(value = "/proxy1")
    public SseEmitter test() throws IOException {
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
    // 测试流式输出： curl -X POST http://localhost:8080/stream/proxy
    @PostMapping(value = "/proxy")
    public SseEmitter proxy() {
        return SseUtil.proxyAPI();
    }

}
