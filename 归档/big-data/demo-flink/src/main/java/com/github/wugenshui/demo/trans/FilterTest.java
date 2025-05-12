package com.github.wugenshui.demo.trans;

import com.github.wugenshui.demo.model.Event;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * 过滤：对数据流执行过滤
 *
 * @author : chenbo
 * @date : 2023-04-04
 */
public class FilterTest {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env =
                StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        DataStreamSource<Event> stream = env.fromElements(
                new Event("Mary", "./home", 1000L),
                new Event("Bob", "./cart", 2000L)
        );

        // 传入匿名类实现 FilterFunction
        SingleOutputStreamOperator<Event> filter = stream.filter(new FilterFunction<Event>() {
            @Override
            public boolean filter(Event e) throws Exception {
                return e.user.equals("Mary");
            }
        });
        filter.print();

        // 传入 FilterFunction 实现类
        stream.filter(new UserFilter()).print();
        env.execute();
    }

    public static class UserFilter implements FilterFunction<Event> {
        @Override
        public boolean filter(Event e) throws Exception {
            return e.user.equals("Mary");
        }
    }
}
