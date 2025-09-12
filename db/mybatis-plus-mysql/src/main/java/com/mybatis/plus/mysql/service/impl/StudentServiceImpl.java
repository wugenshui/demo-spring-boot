package com.mybatis.plus.mysql.service.impl;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.mybatis.plus.mysql.aop.IgnoreTenant;
import com.mybatis.plus.mysql.aop.MybatisTenantContext;
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

    @Override
    public List<Student> listByGender(Integer gender) {
        return lambdaQuery().eq(Student::getGender, gender).list();
    }

    @Override
    public List<Student> or() {
        return lambdaQuery().and(i -> i.and(j -> j.eq(Student::getGender, 1))
                .or(j -> j.eq(Student::getClassId, "31").eq(Student::getGender, 0))
        ).list();
    }

    public List<Student> ignoreTenantWay1() {
        List<Student> list;
        try {
            MybatisTenantContext.set(true);
            list = lambdaQuery().list();
        } finally {
            // 使用完一定要保证clear执行
            MybatisTenantContext.clear();
        }
        return list;
    }

    @IgnoreTenant
    public List<Student> ignoreTenantWay2() {
        return lambdaQuery().list();
    }
}
