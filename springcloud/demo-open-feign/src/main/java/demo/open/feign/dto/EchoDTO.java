package demo.open.feign.dto;

import lombok.Data;

import java.util.Map;

@Data
public class EchoDTO {
    private String url;
    private String method;
    private Map<String, String> query; // 查询参数通常是以键值对的形式存在
    private Object body; // 请求体可以是任意对象，具体取决于API设计
    private Map<String, String> headers;
}