package com.github.wugenshui.demo.trans;

import com.github.wugenshui.demo.model.Event;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * 按键分区
 *
 * @author : chenbo
 * @date : 2023-04-04
 */
public class KeyByTest {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env =
                StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        DataStreamSource<Event> stream = env.fromElements(
                new Event("Mary", "./home", 1000L),
                new Event("Bob", "./cart2", 2000L),
                new Event("Bob", "./cart", 1000L)
        );
        // 第一种方式：使用 Lambda 表达式
        KeyedStream<Event, String> keyedStream = stream.keyBy(e -> e.user);

        // 第二种方式：使用匿名类实现 KeySelector
        KeyedStream<Event, String> keyedStream1 = stream.keyBy(
                new KeySelector<Event, String>() {
                    @Override
                    public String getKey(Event e) throws Exception {
                        return e.user;
                    }
                });

        // 第三种方式：简单聚合,推荐使用，支持 sum,max,min,minBy,maxBy
        stream.keyBy(e -> e.user).sum("timestamp").print();

        env.execute();
    }
}
