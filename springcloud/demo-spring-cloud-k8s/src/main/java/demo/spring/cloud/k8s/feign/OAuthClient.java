package demo.spring.cloud.k8s.feign;

import demo.spring.cloud.k8s.feign.vo.AuthLoginReqVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author : chenbo
 * @date : 2025-01-22
 */
@FeignClient(value="jdi2-service") // 本地调试可以开启url , url = "http://192.168.0.89:8080/"
public interface OAuthClient {
    /**
     * get请求参数传递，路径参数
     * */
    @PostMapping(value = "/api/system/auth/login")
    String login(@RequestBody AuthLoginReqVO reqVO);
}
