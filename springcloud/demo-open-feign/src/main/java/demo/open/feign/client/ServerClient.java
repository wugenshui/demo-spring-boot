package demo.open.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author : chenbo
 * @date : 2025-01-07
 */
@FeignClient(value="ai.h.com", url="http://192.168.0.77:801")
public interface ServerClient {

    /**
     * get请求参数传递，路径参数
     * */
    @GetMapping(value = "/api/system/auth/login")
    String login();
}

