package com.wugenshui.service.impl;

import com.wugenshui.service.IExampleService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

@Service
public class ExampleServiceImpl implements IExampleService {

    @Override
    @Tool(name = "processData", description = "处理输入数据并返回结果")
    public String processData(@ToolParam(description = "输入数据") String input) {
        return String.format("处理后的数据: %s", input.toUpperCase());
    }

    @Override
    @Tool(name = "calculateResult", description = "计算数值结果")
    public String calculateResult(@ToolParam(description = "输入数值") int value) {
        int result = value * 2;
        return String.format("计算结果: %d", result);
    }


}