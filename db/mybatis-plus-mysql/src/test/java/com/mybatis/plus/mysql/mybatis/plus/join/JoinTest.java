package com.mybatis.plus.mysql.mybatis.plus.join;

import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.mybatis.plus.mysql.controller.vo.StudentExtVO;
import com.mybatis.plus.mysql.entity.Class;
import com.mybatis.plus.mysql.entity.Student;
import com.mybatis.plus.mysql.mapper.StudentMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : chenbo
 * @date : 2025-09-12
 */
@SpringBootTest
class JoinTest {
    @Resource
    private StudentMapper studentMapper;

    @Test
    void test() {
        MPJLambdaWrapper<Student> wrapper = new MPJLambdaWrapper<Student>()
                .selectAll(Student.class)
                .select(Class::getClassName)
                .leftJoin(Class.class, Class::getId, Student::getClassId)
                .eq(Student::getGender, 0);
        List<StudentExtVO> students = studentMapper.selectJoinList(StudentExtVO.class, wrapper);
        System.out.println(students);
    }
}
