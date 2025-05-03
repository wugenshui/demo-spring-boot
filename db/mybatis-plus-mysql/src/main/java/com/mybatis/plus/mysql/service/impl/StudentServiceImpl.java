package com.mybatis.plus.mysql.service.impl;

import com.mybatis.plus.mysql.entity.Student;
import com.mybatis.plus.mysql.mapper.StudentMapper;
import com.mybatis.plus.mysql.service.StudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
