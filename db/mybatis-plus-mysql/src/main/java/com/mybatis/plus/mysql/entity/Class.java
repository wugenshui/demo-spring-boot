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
 * 班级
 * </p>
 *
 * @author chenbo
 * @since 2025-05-03
 */
@Getter
@Setter
@TableName("sims_class")
@ApiModel(value = "Class对象", description = "班级")
public class Class implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("所在学院")
    private String collegeId;

    @ApiModelProperty("所属专业ID")
    private String majorId;

    @ApiModelProperty("班级ID")
    @TableId("CLASS_ID")
    private String classId;

    @ApiModelProperty("班级名称")
    private String className;

    @ApiModelProperty("班级人数")
    private Integer studentNumber;

    @ApiModelProperty("辅导员")
    private String adviser;

    @ApiModelProperty("成立时间")
    private Date estabDate;

    @ApiModelProperty("学习年数")
    private Integer yearNumber;

    @ApiModelProperty("租户号")
    private String tenantId;

    @ApiModelProperty("乐观锁")
    private Integer revision;
}
