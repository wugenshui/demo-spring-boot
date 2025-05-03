package com.mybatis.plus.mysql.sims.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 学生
 * </p>
 *
 * @author chenbo
 * @since 2025-05-03
 */
@Getter
@Setter
@TableName("sims_student")
@ApiModel(value = "Student对象", description = "学生")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("所在学院ID")
    private String collegeId;

    @ApiModelProperty("所在班级ID")
    private String classId;

    @ApiModelProperty("学生ID")
    @TableId("STUDENT_ID")
    private String studentId;

    @ApiModelProperty("学生姓名")
    private String studentName;

    @ApiModelProperty("英文名")
    private String engName;

    @ApiModelProperty("身份证号")
    private String idCardNo;

    @ApiModelProperty("手机号")
    private String mobilePhone;

    @ApiModelProperty("性别")
    private String gender;

    @ApiModelProperty("月薪")
    private BigDecimal monthlySalary;

    @ApiModelProperty("出生日期")
    private Date birth;

    @ApiModelProperty("头像")
    private Integer avatar;

    @ApiModelProperty("身高")
    private Integer height;

    @ApiModelProperty("体重")
    private Integer weight;

    @ApiModelProperty("名族")
    private String nation;

    @ApiModelProperty("政治面貌")
    private String political;

    @ApiModelProperty("婚姻状况")
    private String marital;

    @ApiModelProperty("籍贯（省）")
    private String domicilePlaceProvince;

    @ApiModelProperty("籍贯（市）")
    private String domicilePlaceCity;

    @ApiModelProperty("户籍地址")
    private String domicilePlaceAddress;

    @ApiModelProperty("爱好")
    private String hobby;

    @ApiModelProperty("简要介绍")
    private String intro;

    @ApiModelProperty("居住地址")
    private String presentAddress;

    @ApiModelProperty("电子邮件")
    private String email;

    @ApiModelProperty("入学日期")
    private Date entryDate;

    @ApiModelProperty("状态")
    private String status;

    @ApiModelProperty("租户号")
    private String tenantId;

    @ApiModelProperty("乐观锁")
    private Integer revision;

    @ApiModelProperty("创建人")
    private String creater;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新人")
    private String updater;

    @ApiModelProperty("更新时间")
    private Date updateTime;
}
