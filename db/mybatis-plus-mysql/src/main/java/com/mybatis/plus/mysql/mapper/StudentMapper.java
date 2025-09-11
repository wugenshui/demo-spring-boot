package com.mybatis.plus.mysql.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.mybatis.plus.mysql.entity.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 学生 Mapper 接口
 * </p>
 *
 * @author chenbo
 * @since 2025-05-03
 */
public interface StudentMapper extends BaseMapper<Student> {
    default List<Student> list() {
        return selectList(null);
    }

    @InterceptorIgnore(tenantLine = "true")
    default List<Student> listInterceptorIgnore() {
        return selectList(null);
    }
}
