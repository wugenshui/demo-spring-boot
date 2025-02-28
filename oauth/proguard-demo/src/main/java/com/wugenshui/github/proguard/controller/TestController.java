package com.wugenshui.github.proguard.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : chenbo
 * @date : 2025-02-28
 */
@RestController
@RequestMapping("/")
public class TestController {
    @RequestMapping("/")
    public String test() {
        return "Hello World!";
    }
}
