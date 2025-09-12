package com.mybatis.plus.mysql.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mybatis.plus.mysql.entity.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.BadSqlGrammarException;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : chenbo
 * @date : 2025-09-11
 */
@SpringBootTest
class StudentMapperTest {

    @Resource
    private StudentMapper studentMapper;

    @Test
    void student() {
        Student student = new Student();
        student.setStudentName("张");
        student.setGender(0);
        List<Student> students = studentMapper.selectByStudent(student);
        System.out.println(students);
    }

    @Test
    void selectByUserName() {
        String userName = "张";
        List<Student> students = studentMapper.selectByUserName(userName);
        System.out.println(students);
    }

    @Test
    void selectByWrapper() {
        LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Student::getId, 1);
        studentMapper.selectByWrapper(queryWrapper);
    }
}