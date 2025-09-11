package com.mybatis.plus.mysql.entity;

import com.baomidou.mybatisplus.annotation.TableId;
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
 * 学生
 * </p>
 *
 * @author chenbo
 * @since 2025-09-11
 */
@Getter
@Setter
@ToString
@TableName("sims_student")
@ApiModel(value = "Student对象", description = "学生")
public class Student extends BaseEntity {

    /**
     * 所在学院ID
     */
    @ApiModelProperty("所在学院ID")
    private String collegeId;

    /**
     * 所在班级ID
     */
    @ApiModelProperty("所在班级ID")
    private String classId;

    /**
     * 学生ID
     */
    @TableId("STUDENT_ID")
    @ApiModelProperty("学生ID")
    private String studentId;

    /**
     * 学生姓名
     */
    @ApiModelProperty("学生姓名")
    private String studentName;

    /**
     * 英文名
     */
    @ApiModelProperty("英文名")
    private String engName;

    /**
     * 身份证号
     */
    @ApiModelProperty("身份证号")
    private String idCardNo;

    /**
     * 手机号
     */
    @ApiModelProperty("手机号")
    private String mobilePhone;

    /**
     * 性别
     */
    @ApiModelProperty("性别")
    private String gender;

    /**
     * 月薪
     */
    @ApiModelProperty("月薪")
    private BigDecimal monthlySalary;

    /**
     * 出生日期
     */
    @ApiModelProperty("出生日期")
    private Date birth;

    /**
     * 头像
     */
    @ApiModelProperty("头像")
    private Integer avatar;

    /**
     * 身高
     */
    @ApiModelProperty("身高")
    private Integer height;

    /**
     * 体重
     */
    @ApiModelProperty("体重")
    private Integer weight;

    /**
     * 名族
     */
    @ApiModelProperty("名族")
    private String nation;

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
     * 籍贯（省）
     */
    @ApiModelProperty("籍贯（省）")
    private String domicilePlaceProvince;

    /**
     * 籍贯（市）
     */
    @ApiModelProperty("籍贯（市）")
    private String domicilePlaceCity;

    /**
     * 户籍地址
     */
    @ApiModelProperty("户籍地址")
    private String domicilePlaceAddress;

    /**
     * 爱好
     */
    @ApiModelProperty("爱好")
    private String hobby;

    /**
     * 简要介绍
     */
    @ApiModelProperty("简要介绍")
    private String intro;

    /**
     * 居住地址
     */
    @ApiModelProperty("居住地址")
    private String presentAddress;

    /**
     * 电子邮件
     */
    @ApiModelProperty("电子邮件")
    private String email;

    /**
     * 入学日期
     */
    @ApiModelProperty("入学日期")
    private Date entryDate;

    /**
     * 状态
     */
    @ApiModelProperty("状态")
    private String status;

    /**
     * 乐观锁
     */
    @ApiModelProperty("乐观锁")
    private Integer revision;
}
