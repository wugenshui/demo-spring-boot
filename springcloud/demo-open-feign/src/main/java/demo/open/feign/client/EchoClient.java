package demo.open.feign.client;

import demo.open.feign.dto.EchoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author : chenbo
 * @date : 2025-01-07
 */
@FeignClient(value="feign-service-provider", url="http://192.168.0.77:801")
public interface EchoClient {
    /**
     * get请求参数传递，路径参数
     * */
    @GetMapping(value = "/aa/{parma}")
    EchoDTO echo(@PathVariable("parma") String parma);
}
