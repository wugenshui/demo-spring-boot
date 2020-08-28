package com.chenbo.baseutil.child;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@ApiModel("采购统计Dto")
@Data
public class PurchaseStatsDto implements Cloneable {

    /**
     * 数量
     */
    private BigDecimal amount;
    /**
     * 平均单价
     */
    private BigDecimal avgPrice;
    /**
     * 金额合计
     */
    private BigDecimal totalPrice;

    // 上级物资分类

    /**
     * 上级物资分类ID
     */
    private Integer parentTypeId;
    /**
     * 上级物资分类名称
     */
    private String parentTypeName;

    // 物资分类

    /**
     * 物资分类ID
     */
    private Integer typeId;
    /**
     * 物资分类名称
     */
    private String typeName;

    // 物资

    /**
     * 物资ID
     */
    private Integer modelId;
    /**
     * 规格型号
     */
    private String model;
    /**
     * 物资编号
     */
    private String modelCode;

    // 项目

    /**
     * 项目ID
     */
    private Integer projectId;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 供应商
     */
    private String customerName;

    // 分组属性

    /**
     * 物资
     */
    private String material;
    /**
     * 项目群名称
     */
    private String projectGroupName;
    /**
     * 生产批次
     */
    private String batchName;
    /**
     * 部门
     */
    private String organName;
    /**
     * 当前层级
     */
    private Integer curLevel;
    /**
     * 最大层级
     */
    private Integer maxLevel;
    /**
     * 下级节点集合
     */
    private List<PurchaseStatsDto> child;

    /**
     * 只是浅拷贝,用于处理排序字段
     *
     * @return
     * @throws CloneNotSupportedException
     */
    @Override
    public PurchaseStatsDto clone() {
        PurchaseStatsDto dto = null;
        try {
            dto = (PurchaseStatsDto) super.clone();
        } catch (CloneNotSupportedException e) {
            // 这里一般不会出现异常
        }
        return dto;
    }
}