package com.mybatis.plus.mysql.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.mybatis.plus.mysql.entity.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
    List<Student> selectByWrapper(@Param(Constants.WRAPPER) Wrapper<Student> wrapper);
}
