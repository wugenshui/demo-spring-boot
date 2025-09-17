package com.mybatis.plus.mysql.controller.vo;

import com.mybatis.plus.mysql.entity.Student;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author : chenbo
 * @date : 2025-09-12
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class StudentExtVO extends Student {
    private String className;
}
