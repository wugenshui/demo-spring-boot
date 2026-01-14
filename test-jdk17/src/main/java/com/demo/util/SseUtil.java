package com.demo.util;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * SSE 代理工具类
 * 注意：在当前项目中无法使用
 *
 * @author : chenbo
 * @date : 2026-01-09
 */
@Slf4j
public class SseUtil {
    // 事件名称常量
    private static final String EVENT_ERROR = "error";
    private static final String EVENT_RAW = "raw";
    // 其他常量
    private static final String DATA_PREFIX = "data:";
    /**
     * 线程池
     */
    private static final ExecutorService SSE_PROXY_THREAD_POOL = Executors.newCachedThreadPool(r -> {
        Thread t = new Thread(r, "sse-proxy-thread");
        t.setDaemon(true);
        return t;
    });

    /**
     * 代理远程 SSE 流到本地 SseEmitter
     *
     * @param url     远程 SSE 接口地址
     * @param body    请求体
     * @param headers 请求头（如 Authorization）
     */
    public static SseEmitter proxySseStream(String url, JSONObject body, Map<String, String> headers) {
        // 这里可以按实际情况设置过期时间
        SseEmitter emitter = new SseEmitter(0L);
        emitter.onTimeout(() -> {
            log.error("sse 连接超时");
            emitter.complete();
        });
        emitter.onError(throwable -> {
            log.error("sse 连接错误", throwable);
            emitter.complete();
        });

        SSE_PROXY_THREAD_POOL.submit(() -> {
            try (HttpResponse response = HttpRequest.post(url)
                    .addHeaders(headers)
                    .body(body.toString())
                    .setConnectionTimeout(30000) // 设置连接超时时间
                    .timeout(0) // 设置超时为 0 表示不超时（适用于 SSE 长连接）
                    .execute()) {

                int statusCode = response.getStatus();
                if (statusCode != HttpStatus.OK.value()) {
                    sendErrorAndComplete(emitter, String.format("SSE 请求错误，状态码: %s", statusCode), null);
                    return;
                }

                // 获取原始输入流（SSE 是文本流，按行读取）
                try (InputStream inputStream = response.bodyStream();
                     Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                     BufferedReader bufferedReader = new BufferedReader(reader)) {

                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        log.info("收到响应行: {}", line);

                        if (line.trim().isEmpty()) {
                            // 忽略空行
                        } else if (line.startsWith(DATA_PREFIX)) {
                            String data = line.substring(DATA_PREFIX.length()); // 安全截取
                            if (!data.isEmpty()) {
                                emitter.send(SseEmitter.event().data(data).build());
                            }
                        } else {
                            // 非 data: 开头的行（如 event:, id:, retry: 或自定义内容）
                            emitter.send(SseEmitter.event().name(EVENT_RAW).data(line).build());
                        }
                    }

                    // 正常结束流
                    emitter.complete();

                } catch (IOException e) {
                    sendErrorAndComplete(emitter, "SSE 代理异常: " + e.getMessage(), e);
                }
            }
        });

        return emitter;
    }

    /**
     * 发送错误消息并完成
     *
     * @param emitter   发送者
     * @param errorMsg  错误信息
     * @param exception 异常,只会记录到本地日志中，可为空
     *
     */
    private static void sendErrorAndComplete(SseEmitter emitter, String errorMsg, Exception exception) {
        log.error(errorMsg, exception);
        try {
            emitter.send(SseEmitter.event().name(EVENT_ERROR).data(errorMsg).build());
        } catch (IOException e) {
            log.error("sendErrorAndComplete error", e);
        }
        emitter.complete();
    }

    /**
     * 智能体应用
     */
    public static SseEmitter proxyAPI1() {
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

        return proxySseStream("https://ai.test.com/api/chat/api/v1/chat/completions", body, headers);
    }

    /**
     * 大模型
     */
    public static SseEmitter proxyAPI() {
        JSONObject body = JSONUtil.createObj()
                .set("model", "Qwen3-30B-A3B")
                .set("stream", true)
                .set("messages", List.of(Map.of("role", "user", "content", "你是")));

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", "sk-");
        headers.put("Content-Type", "application/json;charset=UTF-8");

        return proxySseStream("http://nginx.llm.test.com:30000/api/qwen3/v1/chat/completions", body, headers);
    }
}
