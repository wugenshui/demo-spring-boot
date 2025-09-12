package com.mybatis.plus.mysql.mybatis.plus;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Sequence;

/**
 * @author : chenbo
 * @date : 2025-09-12
 */
public class IdGeneratorTest {
    public static void main(String[] args) {
        // 推荐使用，19位，顺序
        Sequence sequence = new Sequence(null);
        for (int i = 0; i < 10; i++) {
            System.out.println("nextId = " + sequence.nextId());
        }
        // 长度太长，适合做分布式ID
        for (int i = 0; i < 10; i++) {
            System.out.println("IdWorker.get32UUID() = " + IdWorker.get32UUID());
        }
    }
}
