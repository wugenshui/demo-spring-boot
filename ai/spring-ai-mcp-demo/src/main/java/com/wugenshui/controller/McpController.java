package com.wugenshui.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/mcp1111")
public class McpController {


    /**
     * 协议中包含三种传输方式：
     * 常规请求：客户端发POST请求，服务器返回常规的HTTP响应。
     * 临时长连接：客户端发POST请求，服务器打开一个临时的SSE流，用于针对这个请求和客户端进行必要的交流，在将这个请求的响应发给客户端后，服务器将这个SSE流关闭。
     * 持久长连接：客户端发GET请求，服务器打开一个持久的SSE流，用于服务器往客户端发请求或通知。注意，服务器往客户端发响应时不通过这个SSE流。
     *
     * 在前两种传输方式中，客户端发的POST请求是一样的，由服务器决定响应类型是常规响应（application/json）还是事件流（event-stream）。
     * 只要服务器不主动设置为事件流，第二种传输方式就不会出现。
     * 但第三种传输方式需要特殊处理，因为客户端不知道服务器是否支持持久长连接，它会往消息端点发一个GET请求，如果服务器不支持持久长连接，必须返回405状态码，
     */

    //持久长连接：客户端发GET请求。如果服务器不支持持久长连接，必须返回405状态码。
    @GetMapping
    public void handleGet() {
        throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "不支持持久长连接");
    }

    //常规请求：客户端发POST请求，服务器返回常规的HTTP响应。
    //根据规范，请求体和响应体都是JSON-RPC 2.0格式的字符串
    @PostMapping
    public ResponseEntity<Object> handlePost(@RequestBody String body) {
        // MCP协议处理逻辑
        return ResponseEntity.ok().body("MCP请求处理成功");
    }
}