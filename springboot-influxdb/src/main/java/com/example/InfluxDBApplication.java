package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @version 1.0.0
 * @description: influxdb
 * @author: guorf
 * @time: 2021/11/15 15:19
 */
@SpringBootApplication
public class InfluxDBApplication {

    public static void main(String[] args) {
        SpringApplication.run(InfluxDBApplication.class, args);
    }
}
