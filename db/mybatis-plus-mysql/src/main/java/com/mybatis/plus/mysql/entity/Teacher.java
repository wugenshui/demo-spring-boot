package com.mybatis.plus.mysql.entity;

import com.baomidou.mybatisplus.annotation.TableId;
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
 * 教师
 * </p>
 *
 * @author chenbo
 * @since 2025-09-11
 */
@Getter
@Setter
@ToString
@TableName("sims_teacher")
@ApiModel(value = "Teacher对象", description = "教师")
public class Teacher extends BaseEntity {

    /**
     * 所在学院ID
     */
    @ApiModelProperty("所在学院ID")
    private String collegeId;

    /**
     * 教师ID
     */
    @TableId("TEACHER_ID")
    @ApiModelProperty("教师ID")
    private String teacherId;

    /**
     * 姓名
     */
    @ApiModelProperty("姓名")
    private String teacherName;

    /**
     * 性别
     */
    @ApiModelProperty("性别")
    private String gender;

    /**
     * 出生日期
     */
    @ApiModelProperty("出生日期")
    private Date birth;

    /**
     * 毕业院校
     */
    @ApiModelProperty("毕业院校")
    private String graduateInstitution;

    /**
     * 从业年限
     */
    @ApiModelProperty("从业年限")
    private Integer practiceYears;

    /**
     * 政治面貌
     */
    @ApiModelProperty("政治面貌")
    private String political;

    /**
     * 婚姻状况
     */
    @ApiModelProperty("婚姻状况")
    private String marital;

    /**
     * 头像
     */
    @ApiModelProperty("头像")
    private String avatar;

    /**
     * 介绍
     */
    @ApiModelProperty("介绍")
    private String intro;

    /**
     * 乐观锁
     */
    @ApiModelProperty("乐观锁")
    private Integer revision;
}
