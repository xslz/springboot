package com.example.controller;

import com.example.entity.Temperature;
import lombok.extern.slf4j.Slf4j;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @version 1.0.0
 * @description:
 * @author: guorf
 * @time: 2021/11/15 15:35
 */
@Slf4j
@RestController
@RequestMapping("/insert")
public class InsertController {

    @Autowired
    private InfluxDB influxDB;

    @Value("${spring.influx.database}")
    private String database;

    /**
     * @description: 单体内容插入
     * @param
     * @return: void
     * @author: guorf
     * @time: 2021/11/15 16:44
     */
    @GetMapping("/simple")
    public void simple() {
        log.info("---开始插入数据---");
        influxDB.write(Point.measurement("temperature")
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .tag("machine", "unit42")
                .tag("type", "assembly")
                .addField("external", 11)
                .addField("internal", 22)
                .build());

        influxDB.write(Point.measurement("temperature")
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .tag("machine", "unit43")
                .tag("type", "assembly")
                .addField("external", 33)
                .addField("internal", 44)
                .build());
    }

    /**
     * @description: 对象插入
     * @param
     * @return: void
     * @author: guorf
     * @time: 2021/11/15 16:44
     */
    @GetMapping("/entity")
    public void entity() {
        log.info("---开始插入数据---");
        Temperature temperature1 = new Temperature(System.currentTimeMillis(),
                "unit42", "assembly", 111 ,222);
        influxDB.write(Point.measurementByPOJO(Temperature.class)
                .time(temperature1.getTime(), TimeUnit.MILLISECONDS)
                .addFieldsFromPOJO(temperature1)
                .build());

        Temperature temperature2 = new Temperature(System.currentTimeMillis(),
                "unit43", "assembly", 333 ,444);
        influxDB.write(Point.measurementByPOJO(Temperature.class)
                .time(temperature1.getTime(), TimeUnit.MILLISECONDS)
                .addFieldsFromPOJO(temperature2)
                .build());
    }
}
