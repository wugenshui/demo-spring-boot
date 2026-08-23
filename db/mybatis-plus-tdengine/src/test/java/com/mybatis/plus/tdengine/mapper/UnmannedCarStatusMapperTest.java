package com.mybatis.plus.tdengine.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mybatis.plus.tdengine.entity.UnmannedCarStatusDo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@SpringBootTest
class UnmannedCarStatusMapperTest {
    @Resource
    private UnmannedCarStatusMapper unmannedCarStatusMapper;

    @Test
    void testSql() {
        QueryWrapper<UnmannedCarStatusDo> queryWrapper = new QueryWrapper<>();
        List<UnmannedCarStatusDo> list = unmannedCarStatusMapper.selectList(queryWrapper);
        log.info(list.toString());

        UnmannedCarStatusDo statusDo = UnmannedCarStatusDo.builder()
                .ts(LocalDateTime.now())
                .vin("TESTVIN000000001")
                .messageId("msg-001")
                .powerOnStatus(1)
                .controlModel(2)
                .powerMode(1)
                .chargeStatus(3)
                .lampSwitchStatus(0)
                .battery(95)
                .currentFuelLevel(0)
                .currentBatteryLevel(80)
                .accumulatedMileage(12.3)
                .networkForm(1)
                .createTime(LocalDateTime.now())
                .build();
        unmannedCarStatusMapper.insertRow("unmanned_car_status_" + statusDo.getVin(), statusDo);
    }

}