package com.mybatis.plus.mysql.service;

import com.mybatis.plus.mysql.entity.Student;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : chenbo
 * @date : 2025-09-11
 */
@SpringBootTest
@Transactional
class StudentServiceTest {

    @Resource
    private StudentService studentService;

    @Test
    void mybatisPlusMethodTest() {
        Student student = new Student();
        student.setId("1");
        student.setStudentName("一一");
        student.setGender(1);
        student.setClassId("31");
        studentService.save(student);

        Student student1 = studentService.getById(student.getId());

        student1.setGender(0);
        studentService.updateById(student1);

        studentService.removeById(student1.getId());
    }

    @Test
    void saveOrUpdate() {
        Student student = new Student();
        student.setId("1");
        student.setStudentName("一一");
        student.setClassId("31");
        student.setGender(0);
        // 不推荐使用该方法： 如果设置了id，会先查询是否存在数据，然后进行插入/更新
        studentService.saveOrUpdate(student);
    }

    @Test
    void ignoreTenantWay1() {
        studentService.ignoreTenantWay1();
    }

    @Test
    void ignoreTenantWay2() {
        studentService.ignoreTenantWay2();
    }

    @Test
    void listByGender() {
        List<Student> students = studentService.listByGender(1);
        System.out.println(students);
    }

    @Test
    void or() {
        List<Student> students = studentService.or();
        System.out.println(students);
    }

}