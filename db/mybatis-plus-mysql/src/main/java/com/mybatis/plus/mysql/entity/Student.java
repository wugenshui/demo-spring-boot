package com.mybatis.plus.mysql.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mybatis.plus.mysql.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * <p>
 * 学生
 * </p>
 *
 * @author chenbo
 * @since 2025-09-12
 */
@Getter
@Setter
@ToString
@TableName("sims_student")
@ApiModel(value = "Student对象", description = "学生")
public class Student extends BaseEntity {

    /**
     * 班级ID
     */
    @ApiModelProperty("班级ID")
    private String classId;

    /**
     * 学生姓名
     */
    @ApiModelProperty("学生姓名")
    private String studentName;

    /**
     * 手机号
     */
    @ApiModelProperty("手机号")
    private String phone;

    /**
     * 性别;0=男，1=女
     */
    @ApiModelProperty("性别;0=男，1=女")
    private Integer gender;

    /**
     * 出生日期
     */
    @ApiModelProperty("出生日期")
    private Date birth;

    /**
     * 入学日期
     */
    @ApiModelProperty("入学日期")
    private Date entryDate;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;
}
