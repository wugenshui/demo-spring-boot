package com.github.wugenshui.aop.test.controller;

import com.github.wugenshui.aop.test.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : chenbo
 * @date : 2023-11-13
 */
@RestController
@RequestMapping("/")
public class TestController {

    @Autowired
    private MyService myService;

    @GetMapping
    public String test() {
        return myService.test(1);
    }
}
