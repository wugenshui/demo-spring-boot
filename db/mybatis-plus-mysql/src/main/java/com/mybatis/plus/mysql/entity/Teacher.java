package com.mybatis.plus.mysql.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 教师
 * </p>
 *
 * @author chenbo
 * @since 2025-05-03
 */
@Getter
@Setter
@TableName("sims_teacher")
@ApiModel(value = "Teacher对象", description = "教师")
public class Teacher implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("所在学院ID")
    private String collegeId;

    @ApiModelProperty("教师ID")
    @TableId("TEACHER_ID")
    private String teacherId;

    @ApiModelProperty("姓名")
    private String teacherName;

    @ApiModelProperty("性别")
    private String gender;

    @ApiModelProperty("出生日期")
    private Date birth;

    @ApiModelProperty("毕业院校")
    private String graduateInstitution;

    @ApiModelProperty("从业年限")
    private Integer practiceYears;

    @ApiModelProperty("政治面貌")
    private String political;

    @ApiModelProperty("婚姻状况")
    private String marital;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("介绍")
    private String intro;

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
