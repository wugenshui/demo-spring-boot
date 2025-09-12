package com.mybatis.plus.mysql.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mybatis.plus.mysql.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 教师
 * </p>
 *
 * @author chenbo
 * @since 2025-09-12
 */
@Getter
@Setter
@ToString
@TableName("sims_teacher")
@ApiModel(value = "Teacher对象", description = "教师")
public class Teacher extends BaseEntity {

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private String id;

    /**
     * 班级ID
     */
    @ApiModelProperty("班级ID")
    private String classId;

    /**
     * 姓名
     */
    @ApiModelProperty("姓名")
    private String teacherName;

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
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

    /**
     * 月薪
     */
    @ApiModelProperty("月薪")
    private BigDecimal monthlyPay;
}
