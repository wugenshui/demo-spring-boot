package com.mybatis.plus.mysql.service.impl;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.mybatis.plus.mysql.entity.Student;
import com.mybatis.plus.mysql.mapper.StudentMapper;
import com.mybatis.plus.mysql.service.StudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 学生 服务实现类
 * </p>
 *
 * @author chenbo
 * @since 2025-05-03
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
    public List<Student> serviceList() {
        return lambdaQuery().list();
    }

    @InterceptorIgnore(tenantLine = "true")
    public List<Student> serviceListInterceptorIgnore() {
        return lambdaQuery().list();
    }
}
