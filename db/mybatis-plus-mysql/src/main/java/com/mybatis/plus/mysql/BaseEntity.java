package com.mybatis.plus.mysql;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author : chenbo
 * @date : 2025-05-03
 */
public class BaseEntity {

    @ApiModelProperty("主键")
    private String id;
    
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("创建人")
    private String creater;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("创建时间")
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty("更新人")
    private String updater;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("是否删除")
    @TableLogic
    private Boolean deleted;

    @ApiModelProperty("租户号")
    private String tenantId;
}
