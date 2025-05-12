package com.github.wugenshui.best.practice.single.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 时间格式化处理类
 *
 * @author : chenbo
 * @date : 2020-10-28
 */
@Slf4j
public class DateConverter implements Converter<String, Date> {

    private static final String TIMESTAMP_FORMAT = "^\\d+$";

    private static final Map<String, String> FORMAT_MAP = new HashMap<>();

    private static final String EMPTY_STR = "";

    private static final String NULL_STR = "null";

    static {
        FORMAT_MAP.put("yyyy-MM-dd HH:mm:ss", "^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$");
        FORMAT_MAP.put("yyyy-MM-dd", "^\\d{4}-\\d{1,2}-\\d{1,2}$");
        FORMAT_MAP.put("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "^\\d{4}-\\d{1,2}-\\d{1,2}T{1}\\d{1,2}:\\d{1,2}:\\d{1,2}[.]\\d{1,3}Z$");
        FORMAT_MAP.put("yyyy-MM-dd HH:mm", "^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}$");
        FORMAT_MAP.put("yyyy-MM", "^\\d{4}-\\d{1,2}$");
    }

    @Override
    public Date convert(String value) {
        if (value == null || value.trim().equals(EMPTY_STR) || NULL_STR.equalsIgnoreCase(value)) {
            return null;
        }

        value = value.trim();

        try {
            if (value.matches(TIMESTAMP_FORMAT)) {
                Long lDate = Long.valueOf(value);
                return new Date(lDate);
            } else {
                for (Map.Entry<String, String> entry : FORMAT_MAP.entrySet()) {
                    String regex = entry.getValue();
                    if (value.matches(regex)) {
                        String format = entry.getKey();
                        SimpleDateFormat formatter = new SimpleDateFormat(format);
                        return formatter.parse(value);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(String.format("parser %s to Date fail", value));
        }
        throw new RuntimeException(String.format("parser %s to Date fail", value));
    }
}
