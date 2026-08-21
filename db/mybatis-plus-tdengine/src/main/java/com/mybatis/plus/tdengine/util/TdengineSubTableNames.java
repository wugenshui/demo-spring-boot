package com.mybatis.plus.tdengine.util;

import cn.hutool.core.util.StrUtil;

/**
 * TDengine 子表名：按 vin 生成合法表名，供 INSERT INTO ... USING 超级表 TAGS 使用。
 */
public final class TdengineSubTableNames {

    private static final int MAX_LEN = 120;

    private TdengineSubTableNames() {
    }

    public static String carPosition(String vin) {
        return subTable("ucp", vin);
    }

    public static String carPerformance(String vin) {
        return subTable("ucpf", vin);
    }

    public static String carStatus(String vin) {
        return subTable("ucs", vin);
    }

    private static String subTable(String prefix, String vin) {
        String safe = StrUtil.blankToDefault(vin, "unknown").replaceAll("[^a-zA-Z0-9]", "_");
        if (safe.isEmpty()) {
            safe = "unknown";
        }
        if (Character.isDigit(safe.charAt(0))) {
            safe = "_" + safe;
        }
        String name = prefix + "_" + safe;
        if (name.length() > MAX_LEN) {
            name = name.substring(0, MAX_LEN);
        }
        return name;
    }
}
