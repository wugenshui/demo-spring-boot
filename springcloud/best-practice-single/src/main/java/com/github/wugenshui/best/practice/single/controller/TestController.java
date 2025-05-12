package com.github.wugenshui.best.practice.single.controller;

import com.github.wugenshui.best.practice.single.entity.AjaxResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author : chenbo
 * @date : 2020-12-23
 */
@RestController
@RequestMapping("/")
public class TestController {
    @GetMapping
    public AjaxResult<Date> test() {
        return AjaxResult.success(new Date());
    }
}
