package com.github.wugenshui.baseutil.baseutil.hutool;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author : chenbo
 * @date : 2024-09-06
 */
@SpringBootTest
public class CaptchaTest {
    @Test
    public void test() {
        //定义图形验证码的长和宽
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(100, 50, 4, 64);

        // 图形验证码写出，可以写出到文件，也可以写出到流
        lineCaptcha.write("d:/line.png");
    }
}
