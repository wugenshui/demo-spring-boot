package com.wugenshui.github.proguard;

import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : chenbo
 * @date : 2025-02-28
 */
@SpringBootApplication
@Slf4j
public class App {
    public static void main(String[] args) {


        // 读取dic.txt文件
        File file = new File("oauth/proguard-demo/dic.txt");
        log.info(file.getAbsolutePath() + ":" + file.exists());
        List<String> notSameLines = new ArrayList<>();
        List<String> lines = FileUtil.readLines(file, Charset.defaultCharset());
        lines.forEach(line -> {
            if (notSameLines.contains(line)) {
                log.info("重复的行：" + line);
            } else {
                notSameLines.add(line);
            }
        });
        log.info("lines:{} notSameLines:{}", lines.size(), notSameLines.size());

        SpringApplication.run(App.class, args);
    }
}
