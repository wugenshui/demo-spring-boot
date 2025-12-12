package com.wugenshui.mcpserver.tool;

import org.noear.solon.ai.annotation.ToolMapping;
import org.noear.solon.ai.chat.message.ChatMessage;
import org.noear.solon.ai.mcp.McpChannel;
import org.noear.solon.ai.mcp.server.IMcpServerEndpoint;
import org.noear.solon.ai.mcp.server.annotation.McpServerEndpoint;
import org.noear.solon.annotation.Param;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;

/**
 * 自动构建服务端点服务（使用 springboot 容器） //通过 IMcpServerEndpoint 接口，方便自动收集 McpServerEndpoint 组件类
 */
@Service
@McpServerEndpoint(channel = McpChannel.STREAMABLE, name = "demo1", mcpEndpoint = "/mcp")
public class McpServerTool implements IMcpServerEndpoint {

    @ToolMapping(description = "查询天气预报")
    public String getWeather(@Param(description = "城市位置") String location) {
        return location + " 天气晴，14度";
    }

    @ToolMapping(description = "获取应用版本号")
    public String getAppVersion() {
        return "v3.2.0";
    }

    @ToolMapping(description = "根据用户ID查询邮箱")
    public String getEmail(@Param(description = "用户Id") String user_id) {
        return user_id + "@example.com";
    }

    @ToolMapping(description = "生成关于某个主题的提问")
    public Collection<ChatMessage> askQuestion(@Param(description = "主题") String topic) {
        return Arrays.asList(
                ChatMessage.ofUser("请解释一下'" + topic + "'的概念？")
        );
    }
}