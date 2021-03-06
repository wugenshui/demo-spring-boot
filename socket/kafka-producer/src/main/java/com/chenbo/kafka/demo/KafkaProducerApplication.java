package com.chenbo.kafka.demo;

import com.chenbo.kafka.demo.provider.KafkaSender;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author : chenbo
 * @date : 2019-11-04
 */
@SpringBootApplication
public class KafkaProducerApplication {
    private static final int TRY_TIMES = 3;

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(KafkaProducerApplication.class, args);

        KafkaSender sender = context.getBean(KafkaSender.class);

        for (int i = 0; i < TRY_TIMES; i++) {
            //调用消息发送类中的消息发送方法
            sender.send();

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
