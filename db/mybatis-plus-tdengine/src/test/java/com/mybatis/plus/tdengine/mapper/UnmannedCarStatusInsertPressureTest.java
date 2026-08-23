package com.mybatis.plus.tdengine.mapper;

import com.mybatis.plus.tdengine.entity.UnmannedCarStatusDo;
import com.mybatis.plus.tdengine.util.TdengineSubTableNames;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 单线程批量写入压测：统计 insertBatch 每秒可插入条数。
 * 可改 TOTAL_ROWS / BATCH_SIZE 后在 IDE 单独跑本类。
 */
@Slf4j
@SpringBootTest
class UnmannedCarStatusInsertPressureTest {

    private static final String VIN = "STRESSVIN00000001";
    private static final int WARMUP_ROWS = 100;
    private static final int TOTAL_ROWS = 100000;
    private static final int BATCH_SIZE = 100;

    @Resource
    private UnmannedCarStatusMapper unmannedCarStatusMapper;

    @Test
    void insertBatchThroughput() {
        String subTableName = TdengineSubTableNames.carStatus(VIN);
        LocalDateTime baseTs = LocalDateTime.now();

        insertBatch(subTableName, VIN, buildRows(VIN, baseTs.minusSeconds(10), 0, WARMUP_ROWS));

        int inserted = 0;
        long startNs = System.nanoTime();
        int batchIndex = 0;
        while (inserted < TOTAL_ROWS) {
            int size = Math.min(BATCH_SIZE, TOTAL_ROWS - inserted);
            unmannedCarStatusMapper.insertBatch(
                    subTableName, VIN, buildRows(VIN, baseTs, inserted, size));
            inserted += size;
            batchIndex++;
        }
        long elapsedNs = System.nanoTime() - startNs;
        double elapsedSec = elapsedNs / 1_000_000_000.0;
        double rowsPerSec = inserted / elapsedSec;

        log.info("TDengine insertBatch 压测: vin={}, 批次={}, 每批={}, 总条数={}, 耗时={} ms, 吞吐={} rows/s",
                VIN, batchIndex, BATCH_SIZE, inserted,
                TimeUnit.NANOSECONDS.toMillis(elapsedNs),
                String.format("%.0f", rowsPerSec));
    }

    private void insertBatch(String subTableName, String vin, List<UnmannedCarStatusDo> rows) {
        unmannedCarStatusMapper.insertBatch(subTableName, vin, rows);
    }

    /**
     * ts 按毫秒递进，避免同一子表主键冲突被覆盖。
     */
    private static List<UnmannedCarStatusDo> buildRows(String vin, LocalDateTime baseTs, int startIndex, int size) {
        LocalDateTime createTime = LocalDateTime.now();
        List<UnmannedCarStatusDo> rows = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            int seq = startIndex + i;
            rows.add(UnmannedCarStatusDo.builder()
                    .ts(baseTs.plusNanos(seq * 1_000_000L))
                    .vin(vin)
                    .messageId("stress-" + seq)
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
                    .createTime(createTime)
                    .build());
        }
        return rows;
    }
}
