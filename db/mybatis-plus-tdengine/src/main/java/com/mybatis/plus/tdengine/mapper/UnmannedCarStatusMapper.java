package com.mybatis.plus.tdengine.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mybatis.plus.tdengine.dto.TdengineSubTableBatch;
import com.mybatis.plus.tdengine.entity.UnmannedCarStatusDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 文松
 * @description 针对表【unmanned_car_status】的数据库操作Mapper
 * @createDate 2026-06-07 20:44:22
 * @Entity com.huaweisoft.jdi2.service.jdi2.scheduler.module.unmanned.entity.UnmannedCarStatus
 */
@Mapper
public interface UnmannedCarStatusMapper extends BaseMapper<UnmannedCarStatusDo> {


    /**
     * 查询车辆最新状态
     *
     * @param vin 车辆识别码
     * @return 最新状态
     */
    UnmannedCarStatusDo selectLatest(@Param("vin") String vin);


    /**
     * 查询车辆状态信息列表
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 车辆状态信息列表
     */
    List<UnmannedCarStatusDo> selectListStatus(LocalDateTime startTime, LocalDateTime endTime);

    int insertRow(@Param("subTableName") String subTableName, @Param("row") UnmannedCarStatusDo row);

    int insertBatch(@Param("subTableName") String subTableName, @Param("vin") String vin,
                    @Param("rows") List<UnmannedCarStatusDo> rows);

    int insertMultiTableBatch(@Param("groups") List<TdengineSubTableBatch<UnmannedCarStatusDo>> groups);

}




