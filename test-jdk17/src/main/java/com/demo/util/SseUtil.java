package com.demo.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * SSE 代理工具类
 * 注意：在当前项目中无法使用
 *
 * @author : chenbo
 * @date : 2026-01-09
 */
@Slf4j
public class SseUtil {

    /**
     * 最大可创建线程数，根据业务负载调整
     */
    private static final int MAXIMUM_POOL_SIZE = 50;

    // 事件名称常量
    private static final String EVENT_ERROR = "error";
    private static final String DATA_PREFIX = "data:";
    private static final String EVENT_PREFIX = "event:";

    /**
     * 线程池
     */
    private static final ExecutorService SSE_PROXY_THREAD_POOL = new ThreadPoolExecutor(
            5,
            MAXIMUM_POOL_SIZE,
            10, TimeUnit.MINUTES,
            new LinkedBlockingQueue<>(MAXIMUM_POOL_SIZE), // 有界队列，防止无限堆积
            r -> {
                Thread t = new Thread(r, "sse-proxy");
                t.setDaemon(true);
                return t;
            },
            new ThreadPoolExecutor.AbortPolicy() // 拒绝策略：由调用者线程执行（可选）
    );

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

                    StringBuilder eventData = new StringBuilder();
                    String event = ""; // 默认事件类型

                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        // 空行 → 事件结束，发送
                        if (line.trim().isEmpty()) {
                            // data 或 event 不为空
                            if (!eventData.isEmpty() || StrUtil.isNotEmpty(event)) {
                                String finalData = eventData.toString();
                                log.debug("SSE 事件[{}] 数据: {}", event, finalData);
                                SseEmitter.SseEventBuilder eventBuilder = SseEmitter.event();
                                if (StrUtil.isNotEmpty(event)) {
                                    eventBuilder.name(event);
                                }
                                emitter.send(eventBuilder.data(finalData).build());

                                // 重置事件字段
                                eventData.setLength(0);
                                event = "";
                            }
                        } else if (line.startsWith(DATA_PREFIX)) {
                            String data = line.substring(DATA_PREFIX.length()); // 安全截取
                            if (!data.isEmpty()) {
                                eventData.append(data);
                            }
                        } else if (line.startsWith(EVENT_PREFIX)) {
                            event = line.substring(EVENT_PREFIX.length());
                        } else {
                            //（如 id:, retry: 或自定义内容）
                            log.info("不支持的格式: {}", line);
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
}
