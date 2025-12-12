package com.wugenshui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author : chenbo
 * @date : 2025-12-12
 */
@SpringBootApplication
public class App {
    public static void main(String[] args) {
        /**
         * 2025年3月26日，Model Context Protocol（简称MCP）迎来了一项重要的技术更新：正式将默认传输机制由原先的 HTTP + SSE 切换为 Streamable HTTP。
         * 这一调整标志着 MCP 在协议层面对大规模、高并发AI应用支持的进一步优化，旨在解决长期以来困扰开发者的连接稳定性与服务器资源消耗等核心问题。
         */
        SpringApplication.run(App.class, args);
    }
}
