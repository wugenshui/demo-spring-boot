package com.demo.util;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.BufferedReader;
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
    private static final String EVENT_DONE = "done";
    private static final String EVENT_MESSAGE = "message";
    // 其他常量
    private static final String DATA_PREFIX = "data: ";
    private static final String DONE_MARKER = "[DONE]";
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
            try {
                // 定义处理响应方法
                ResponseHandler<Boolean> responseHandler = response -> {
                    try {
                        if (response.getStatusLine().getStatusCode() != HttpStatus.OK.value()) {
                            log.error("请求大模型错误: {}", response.getStatusLine().getStatusCode());
                            emitter.send(SseEmitter.event().name(EVENT_ERROR).data("请求失败: " + response.getStatusLine().getStatusCode()).build());
                            emitter.complete();
                            return false;
                        } else {
                            try (InputStream stream = response.getEntity().getContent();
                                 Reader reader = new InputStreamReader(stream, StandardCharsets.UTF_8);
                                 BufferedReader bufferedReader = new BufferedReader(reader)) {
                                String line;
                                while ((line = bufferedReader.readLine()) != null) {
                                    log.info("收到响应行: {}", line);
                                    if (line.trim().isEmpty()) {
                                        // 忽略空行
                                        continue;
                                    } else if (line.startsWith(DATA_PREFIX)) {
                                        String data = line.substring(6);
                                        if (DONE_MARKER.equals(data)) {
                                            emitter.send(SseEmitter.event().name(EVENT_DONE).data("").build());
                                            emitter.complete();
                                            return true;
                                        }
                                        if (!data.isEmpty()) {
                                            emitter.send(SseEmitter.event().name(EVENT_MESSAGE).data(data).build());
                                        }
                                    } else {
                                        // 发送原始数据
                                        emitter.send(SseEmitter.event().name(EVENT_RAW).data(line).build());
                                    }
                                }
                                // 流结束后发送完成信号
                                emitter.send(SseEmitter.event().name(EVENT_DONE).data("对话完成").build());
                                emitter.complete();
                                return true;
                            }
                        }
                    } catch (Exception e) {
                        log.error("读取响应错误", e);
                        try {
                            emitter.send(SseEmitter.event().name(EVENT_ERROR).data("读取响应错误: " + e.getMessage()).build());
                        } catch (Exception sendError) {
                            log.error("发送错误消息失败", sendError);
                        }
                        emitter.complete();
                        return false;
                    }
                };

                // 发起请求
                HttpPost post = new HttpPost(url);
                if (headers != null) {
                    headers.forEach(post::addHeader);
                }
                try (CloseableHttpClient client = HttpClients.createDefault()) {
                    StringEntity entity = new StringEntity(body.toString(), StandardCharsets.UTF_8.name());
                    post.setEntity(entity);
                    CloseableHttpResponse execute = client.execute(post);
                    try (CloseableHttpResponse response = execute) {
                        responseHandler.handleResponse(response);
                    }
                } catch (Exception e) {
                    log.error("sse 接口请求异常");
                }
            } catch (Exception e) {
                log.error("请求大模型错误", e);
                try {
                    emitter.send(SseEmitter.event().name(EVENT_ERROR).data("请求错误: " + e.getMessage()).build());
                } catch (Exception sendError) {
                    log.error("发送错误消息失败", sendError);
                }
                emitter.complete();
            }
        });

        return emitter;
    }

    /**
     * 智能体应用
     */
    public static SseEmitter proxyAPI1() {
        JSONObject body = JSONUtil.parseObj(Map.of(
                "appId", "613564319623282688",
                "stream", true,
                "chatId", "001",
                "caller", "test",
                "replyOrigin", 1,
                "messages", List.of(Map.of(
                        "role", "user",
                        "content", "你是"
                ))
        ));

        HashMap<String, String> headers = new HashMap();
        headers.put("Authorization", "hwllm-611026796183289856ldNl3ha");
        headers.put("Content-Type", "application/json;charset=UTF-8");

        return proxySseStream("https://ai.test.com/api/chat/api/v1/chat/completions", body, headers);
    }

    /**
     * 大模型
     */
    public static SseEmitter proxyAPI() {
        JSONObject body = JSONUtil.parseObj(Map.of(
                "model", "Qwen3-30B-A3B",
                "stream", true,
                "messages", List.of(Map.of(
                        "role", "user",
                        "content", "你是"
                ))
        ));

        HashMap<String, String> headers = new HashMap();
        headers.put("Authorization", "sk-");
        headers.put("Content-Type", "application/json;charset=UTF-8");

        return proxySseStream("http://nginx.llm.test.com:30000/api/qwen3/v1/chat/completions", body, headers);
    }
}
