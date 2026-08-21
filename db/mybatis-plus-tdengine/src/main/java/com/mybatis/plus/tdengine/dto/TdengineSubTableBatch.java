package com.mybatis.plus.tdengine.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * TDengine 多子表合并 INSERT 的单个子表批次。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TdengineSubTableBatch<T> {

    private String subTableName;
    private String vin;
    private List<T> rows;
}
