package demo.spring.cloud.k8s.controller;

import demo.spring.cloud.k8s.feign.OAuthClient;
import demo.spring.cloud.k8s.feign.vo.AuthLoginReqVO;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO 测试用，需要删除
 *
 * @author : hw
 * @date : 2023-11-02
 */
@RestController
@RequestMapping("/")
public class TestController {
    @Resource
    private OAuthClient jdi2OAuthClient;

    @GetMapping
    public String test() {
        return "Hello World!";
    }

    @GetMapping("/login")
    public String login() {
        return jdi2OAuthClient.login(new AuthLoginReqVO("admin","jVe2w+gaTlBmpJLJKJGKxw=="));
    }
}
