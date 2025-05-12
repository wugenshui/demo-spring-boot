package com.github.wugenshui.demo.model;

import java.sql.Timestamp;

/**
 * 网站的访问操作实体
 *
 * @author : chenbo
 * @date : 2023-04-04
 */
public class Event {
    public String user;
    public String url;
    public Long timestamp;

    public Event() {
    }

    public Event(String user, String url, Long timestamp) {
        this.user = user;
        this.url = url;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Event{" +
                "user='" + user + '\'' +
                ", url='" + url + '\'' +
                ", timestamp=" + new Timestamp(timestamp) +
                '}';
    }
}
