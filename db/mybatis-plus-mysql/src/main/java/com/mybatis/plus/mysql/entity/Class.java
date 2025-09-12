package com.mybatis.plus.mysql.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mybatis.plus.mysql.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * <p>
 * 班级
 * </p>
 *
 * @author chenbo
 * @since 2025-09-12
 */
@Getter
@Setter
@ToString
@TableName("sims_class")
@ApiModel(value = "Class对象", description = "班级")
public class Class extends BaseEntity {

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private String id;

    /**
     * 班级名称
     */
    @ApiModelProperty("班级名称")
    private String className;
}
