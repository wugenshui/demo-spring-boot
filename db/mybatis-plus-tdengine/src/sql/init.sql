-- 创建数据库及设置过期策略
CREATE DATABASE IF NOT EXISTS car_dev
KEEP 90
DURATION 10d

USE car_dev;

CREATE STABLE IF NOT EXISTS unmanned_car_status (
    ts                  TIMESTAMP,--车辆上报时间戳
    message_id          varchar(64),--消息id
    power_on_status     INT,--上电状态，0x01：上电状态；0x02：下电状态；0x03：其他状态；0xFE：异常；0xFF：无效
    control_model       INT,--控制模式，0x01：人工驾驶模式；0x02：自动驾驶模式；0x03：人工干预模式；0x04：远程控制模式；0xFE：异常；0xFF：无效
    power_mode          INT,--动力模式，0x01：纯电；0x02：混动；0x03：燃油；0x04：燃料电池；0xFE：异常；0xFF：无效
    charge_status       INT,--充电状态，0x01：停车充电；0x02：行驶充电；0x03：未充电状态；0x04：充电完成；0xFE：异常；0xFF：无效
    lamp_switch_status  INT,--车灯开关状态，每位表示灯光的开关状态，0：关闭；1：打开，0：远光灯；1：近光灯；2：左转向灯；3：右转向灯；4：雾灯；5：双闪灯；6：刹车灯；0xFE：异常；0xFF：无效
    battery             INT,--电池SoH，有效值范围：0～100（表示0 %～100 %），最小计量单元：1 %。0xFE：异常；0xFF：无效
    current_fuel_level  INT,--当前油量，有效值范围：0～100（表示0 %～100 %），最小计量单元：1 %。0xFE：异常，0xFF：无效
    current_battery_level INT, --当前电量，有效值范围：0～100（表示0 %～100 %），最小计量单元：1 %。0xFE：异常；0xFF：无效
    accumulated_mileage DOUBLE, --累计里程，有效值范围：0～9999999（表示0 km～999999.9km），最小计量单元：0.1 km。"0xFF，0xFF，0xFF，0xFE"：异常；"0xFF，0xFF，0xFF，0xFF"：无效
    network_form        INT,--网络形态，每bit 代表不同的网络接入模式（1 代表可用状态，0 代表不可用）：1：LTE-V2X；2：5G NR-V2X；3：短距通信；4：其他；0xFE：异常；0xFF：无效
    create_time         TIMESTAMP --创建时间
)
TAGS (
    vin varchar(50) --车辆识别代号
);

-- TDengine表结构
CREATE STABLE unmanned_car_position (
    ts              TIMESTAMP       , -- 车辆上报时间戳
    message_id      varchar(64),  --消息id
    longitude       DOUBLE           ,--经度
    latitude        DOUBLE           ,--纬度
    effective_flag  TINYINT          ,--位置信息有效性标志，0x00：位置有效；0x01：位置无效
    coordinate_type TINYINT          ,--坐标类型，0x01：GCJ02坐标；0x02：CGCS2000坐标；0x03：WGS84坐标；0x04：自定义坐标系
    create_time     TIMESTAMP     --创建时间
)
TAGS (
    vin varchar(50) -- 车辆唯一标识，作为标签
)
COMMENT '无人车辆位置信息表';


-- 创建无人车性能数据超级表
CREATE STABLE unmanned_car_performance (
    ts                          TIMESTAMP        -- 车辆上报时间戳（毫秒级时序主键）
    ,message_id                  VARCHAR(64)      -- 消息id
    ,speed                       DOUBLE            -- 车辆速度，有效值范围：0～350（偏移量100km/h，表示-100km/h～250km/h）
    ,horizontal_acceleration     DOUBLE           -- 车辆横向加速度（偏移量50m/s²，表示-50m/s²~50m/s²）
    ,longitudinal_acceleration   DOUBLE           -- 车辆纵向加速度（偏移量50m/s²，表示-50m/s²~50m/s²）
    ,heading_angle               INT              -- 航向角，有效值范围：0~360°
    ,horizontal_angular_velocity DOUBLE         -- 车辆横摆角速度（偏移量200°/s，表示-200°/s~200°/s）
    ,roll_angular_velocity       DOUBLE              -- 车辆侧倾角速度，有效值范围：0～800（偏移量400°/s，表示-400°/s~400°/s）
    ,pitch_angular_velocity      DOUBLE           -- 俯仰角速度，有效值为[0,1500]*0.1，偏移量750*0.1，范围：[-75°/s,75°/s]
    ,gear                        INT          -- 挡位信息，0：未知，1：P，2：R，3：N，4：D，0xFE：异常，0xFF：无效
    ,throttle_angle              INT              -- 加速踏板开度，有效值范围：0～100（表示0°～100°）
    ,brake_status                INT          -- 制动状态，0x01：制动，0x02：释放，0xFE：异常，0xFF：无效
    ,brake_pedal_angle           INT              -- 制动踏板开度，有效值范围：0～100（表示0%～100%）
    ,steering_wheel_angle        BIGINT           -- 转向盘角度，有效值范围：0～1700（偏移量850°，表示-850°～850°）
    ,vehicle_distance            DOUBLE           -- 前车距离，有效值最大范围：0～40000（表示0m～400m），单位：0.01m
    ,create_time                 TIMESTAMP        -- 入库创建时间
)
TAGS (
    vin VARCHAR(50) -- 车辆识别代号（分区标签）
) COMMENT '无人车性能数据表';

-- 8.19 已执行20楼环境