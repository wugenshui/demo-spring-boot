package com.github.wugenshui.demo.trans;

import com.github.wugenshui.demo.model.Event;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * 映射，将数据流中的数据进行转换
 *
 * @author : chenbo
 * @date : 2023-04-04
 */
public class MapTest {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env =
                StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        DataStreamSource<Event> stream = env.fromElements(
                new Event("Mary", "./home", 1000L),
                new Event("Bob", "./cart", 2000L)
        );

        // 第一种方式：传入匿名类，实现 MapFunction
        SingleOutputStreamOperator<String> map = stream.map(new MapFunction<Event, String>() {
            @Override
            public String map(Event e) throws Exception {
                return e.user;
            }
        });
        map.print();

        // 第二种方式：传入 MapFunction 的实现类
        stream.map(new UserExtractor()).print();

        env.execute();
    }

    public static class UserExtractor implements MapFunction<Event, String> {
        @Override
        public String map(Event e) throws Exception {
            return e.user;
        }
    }
}
