package com.wugenshui.github.proguard.controller;

import com.wugenshui.github.proguard.Util;
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
        System.out.println(Util.test());
        return "Hello World!";
    }
}
