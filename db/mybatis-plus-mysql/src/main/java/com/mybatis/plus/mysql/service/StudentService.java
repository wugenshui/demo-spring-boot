package com.mybatis.plus.mysql.service;

import com.mybatis.plus.mysql.entity.Student;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 学生 服务类
 * </p>
 *
 * @author chenbo
 * @since 2025-05-03
 */
public interface StudentService extends IService<Student> {

    List<Student> listByGender(Integer gender);

    List<Student> or();

    List<Student> ignoreTenantWay1();

    List<Student> ignoreTenantWay2();
}
