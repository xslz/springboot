package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

/**
 * @version 1.0.0
 * @description:
 * @author: guorf
 * @time: 2021/11/15 16:43
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Measurement(name = "temperature")
public class Temperature {

    // Column中的name为measurement中的列名
    // 此外,需要注意InfluxDB中时间戳均是以UTC时保存,在保存以及提取过程中需要注意时区转换
    @Column(name = "time")
    private Long time;
    // 注解中添加tag = true,表示当前字段内容为tag内容
    @Column(name = "machine", tag = true)
    private String machine;
    @Column(name = "type", tag = true)
    private String type;
    @Column(name = "external")
    private Integer external;
    @Column(name = "internal")
    private Integer internal;
}
