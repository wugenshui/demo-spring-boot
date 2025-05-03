package com.mybatis.plus.mysql.sims.entity;

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
 * 学院
 * </p>
 *
 * @author chenbo
 * @since 2025-05-03
 */
@Getter
@Setter
@TableName("sims_college")
@ApiModel(value = "College对象", description = "学院")
public class College implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("学院ID")
    @TableId("COLLEGE_ID")
    private String collegeId;

    @ApiModelProperty("学院名称")
    private String collegeName;

    @ApiModelProperty("学院简称")
    private String shortName;

    @ApiModelProperty("学院介绍")
    private String intro;

    @ApiModelProperty("专业个数")
    private Integer professionNumber;

    @ApiModelProperty("学生人数")
    private Integer studentNumber;

    @ApiModelProperty("院长")
    private String president;

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
