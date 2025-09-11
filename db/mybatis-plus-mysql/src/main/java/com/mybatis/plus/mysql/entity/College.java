package com.mybatis.plus.mysql.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mybatis.plus.mysql.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * <p>
 * 学院
 * </p>
 *
 * @author chenbo
 * @since 2025-09-11
 */
@Getter
@Setter
@ToString
@TableName("sims_college")
@ApiModel(value = "College对象", description = "学院")
public class College extends BaseEntity {

    /**
     * 学院ID
     */
    @TableId("COLLEGE_ID")
    @ApiModelProperty("学院ID")
    private String collegeId;

    /**
     * 学院名称
     */
    @ApiModelProperty("学院名称")
    private String collegeName;

    /**
     * 学院简称
     */
    @ApiModelProperty("学院简称")
    private String shortName;

    /**
     * 学院介绍
     */
    @ApiModelProperty("学院介绍")
    private String intro;

    /**
     * 专业个数
     */
    @ApiModelProperty("专业个数")
    private Integer professionNumber;

    /**
     * 学生人数
     */
    @ApiModelProperty("学生人数")
    private Integer studentNumber;

    /**
     * 院长
     */
    @ApiModelProperty("院长")
    private String president;

    /**
     * 乐观锁
     */
    @ApiModelProperty("乐观锁")
    private Integer revision;
}
