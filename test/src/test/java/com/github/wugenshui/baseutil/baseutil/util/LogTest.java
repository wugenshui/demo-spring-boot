package com.github.wugenshui.baseutil.baseutil.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author : chenbo
 * @date : 2020-05-29
 */
@SpringBootTest
@Slf4j
public class LogTest {

    @Test
    @Ignore
    public void logTest() throws InterruptedException {
        for (int i = 0; ; i++) {
            log.trace(i + "jwbfi2byfh28gf782EHF23F21H78豆腐3ihhibdiewhf都不欸我对俄万佛i额外部分哦i恶化违法和123");
            log.debug(i + "jwbfi2byfh28gf782EHF23F21H78豆腐3ihhibdiewhf都不欸我对俄万佛i额外部分哦i恶化违法和123");
            log.info(i + "jwbfi2byfh28gf782EHF23F21H78豆腐3ihhibdiewhf都不欸我对俄万佛i额外部分哦i恶化违法和123");
            log.warn(i + "jwbfi2byfh28gf782EHF23F21H78豆腐3ihhibdiewhf都不欸我对俄万佛i额外部分哦i恶化违法和123");
            log.error(i + "jwbfi2byfh28gf782EHF23F21H78豆腐3ihhibdiewhf都不欸我对俄万佛i额外部分哦i恶化违法和123");
            Thread.sleep(10);
        }
    }

}
