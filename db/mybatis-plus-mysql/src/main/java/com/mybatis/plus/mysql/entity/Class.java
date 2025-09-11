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
 * 班级
 * </p>
 *
 * @author chenbo
 * @since 2025-09-11
 */
@Getter
@Setter
@ToString
@TableName("sims_class")
@ApiModel(value = "Class对象", description = "班级")
public class Class extends BaseEntity {

    /**
     * 所在学院
     */
    @ApiModelProperty("所在学院")
    private String collegeId;

    /**
     * 所属专业ID
     */
    @ApiModelProperty("所属专业ID")
    private String majorId;

    /**
     * 班级ID
     */
    @TableId("CLASS_ID")
    @ApiModelProperty("班级ID")
    private String classId;

    /**
     * 班级名称
     */
    @ApiModelProperty("班级名称")
    private String className;

    /**
     * 班级人数
     */
    @ApiModelProperty("班级人数")
    private Integer studentNumber;

    /**
     * 辅导员
     */
    @ApiModelProperty("辅导员")
    private String adviser;

    /**
     * 成立时间
     */
    @ApiModelProperty("成立时间")
    private Date estabDate;

    /**
     * 学习年数
     */
    @ApiModelProperty("学习年数")
    private Integer yearNumber;

    /**
     * 乐观锁
     */
    @ApiModelProperty("乐观锁")
    private Integer revision;
}
