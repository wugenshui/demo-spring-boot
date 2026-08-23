package com.mybatis.plus.tdengine.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mybatis.plus.tdengine.entity.UnmannedCarStatusDo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

        UnmannedCarStatusDo statusDo = new UnmannedCarStatusDo();
        unmannedCarStatusMapper.insertRow("unmanned_car_status_" + statusDo.getVin(), statusDo);
    }

}