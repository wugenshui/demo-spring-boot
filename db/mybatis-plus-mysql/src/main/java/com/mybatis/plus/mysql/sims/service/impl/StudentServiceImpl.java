package com.mybatis.plus.mysql.sims.service.impl;

import com.mybatis.plus.mysql.sims.entity.Student;
import com.mybatis.plus.mysql.sims.mapper.StudentMapper;
import com.mybatis.plus.mysql.sims.service.IStudentService;
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
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

}
