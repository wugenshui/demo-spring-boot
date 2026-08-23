package com.mybatis.plus.tdengine.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 无人车辆状态信息
 * @TableName unmanned_car_status
 */
@TableName(value ="unmanned_car_status")
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnmannedCarStatusDo {
    /**
     * 车辆上报时间戳
     */
    @TableId(value = "ts", type = IdType.INPUT)
    private LocalDateTime ts;

    /**
     * 车辆识别代号
     */
    private String vin;

    /**
     * 消息id
     */
    private String messageId;

    /**
     * 上电状态，0x01：上电状态；0x02：下电状态；0x03：其他状态；0xFE：异常；0xFF：无效
     */
    private Integer powerOnStatus;

    /**
     *  控制模式，0x01：人工驾驶模式；0x02：自动驾驶模式；0x03：人工干预模式；0x04：远程控制模式；0xFE：异常；0xFF：无效
     */
    private Integer controlModel;

    /**
     * 动力模式，0x01：纯电；0x02：混动；0x03：燃油；0x04：燃料电池；0xFE：异常；0xFF：无效
     */
    private Integer powerMode;

    /**
     * 充电状态，0x01：停车充电；0x02：行驶充电；0x03：未充电状态；0x04：充电完成；0xFE：异常；0xFF：无效
     */
    private Integer chargeStatus;

    /**
     * 车灯开关状态，每位表示灯光的开关状态，0：关闭；1：打开，0：远光灯；1：近光灯；2：左转向灯；3：右转向灯；4：雾灯；5：双闪灯；6：刹车灯；0xFE：异常；0xFF：无效
     */
    private Integer lampSwitchStatus;

    /**
     * 电池SoH，有效值范围：0～100（表示0 %～100 %），最小计量单元：1 %。0xFE：异常；0xFF：无效
     */
    private Integer battery;

    /**
     * 当前油量，有效值范围：0～100（表示0 %～100 %），最小计量单元：1 %。0xFE：异常，0xFF：无效
     */
    private Integer currentFuelLevel;

    /**
     * 当前电量，有效值范围：0～100（表示0 %～100 %），最小计量单元：1 %。0xFE：异常；0xFF：无效
     */
    private Integer currentBatteryLevel;

    /**
     * 累计里程，有效值范围：0～9999999（表示0 km～999999.9km），最小计量单元：0.1 km。"0xFF，0xFF，0xFF，0xFE"：异常；"0xFF，0xFF，0xFF，0xFF"：无效
     */
    private Double accumulatedMileage;

    /**
     * 网络形态，每bit 代表不同的网络接入模式（1 代表可用状态，0 代表不可用）：1：LTE-V2X；2：5G NR-V2X；3：短距通信；4：其他；0xFE：异常；0xFF：无效
     */
    private Integer networkForm;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}