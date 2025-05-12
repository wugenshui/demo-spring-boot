package com.github.wugenshui.aop.test.service;

import com.github.wugenshui.aop.test.aop0.annotation.DataPermission;
import com.github.wugenshui.aop.test.aop1.annotation.MethodLog;
import org.springframework.stereotype.Service;

/**
 * @author : chenbo
 * @date : 2023-11-13
 */
@DataPermission(enable = false)
@Service
public class MyService {

    @MethodLog
    public String test(int number) {
        return "test" + number;
    }
}
