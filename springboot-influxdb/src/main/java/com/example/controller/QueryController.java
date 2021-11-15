package com.example.controller;

import com.example.entity.Temperature;
import lombok.extern.slf4j.Slf4j;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.0.0
 * @description:
 * @author: guorf
 * @time: 2021/11/15 16:26
 */
@Slf4j
@RestController
@RequestMapping("/query")
public class QueryController {

    @Autowired
    private InfluxDB influxDB;

    @Value("${spring.influx.database}")
    private String database;

    @GetMapping("/simple")
    public void simple() {
        log.info("---开始查询数据---");
        // InfluxDB支持分页查询,因此可以设置分页查询条件
        int page = 1; // 起始页（从0开始）
        int pageSize = 10;  // 每页条目数
        String pageQuery = " LIMIT " + pageSize + " OFFSET " + (page - 1) * pageSize;
        String queryCondition = "";  //查询条件暂且为空
        // 此处查询所有内容,如果
        String queryCmd = "SELECT * FROM "
                // 要指定从 RetentionPolicyName.measurement中查询指定数据,默认的策略可以不加；
                // + 策略name + "." + measurement
                + "temperature"
                // 添加查询条件(注意查询条件选择tag值,选择field数值会严重拖慢查询速度)
                + queryCondition
                // 查询结果需要按照时间排序
                + " ORDER BY time DESC"
                // 添加分页查询条件
                + pageQuery
                // 设置查询时区为（北京时间）
                + " tz('Asia/Shanghai')";

        // 开始查询
        QueryResult queryResult = influxDB.query(new Query(queryCmd, database));
        log.info("原始结果为：" + queryResult);

        // 获取查询结果
        List<QueryResult.Result> results = queryResult.getResults();
        if (results == null) {
            return;
        }
        // 多个sql用分号隔开，因本次查询只有一个sql，所以取第一个就行
        QueryResult.Result result = results.get(0);
        List<QueryResult.Series> seriesList = result.getSeries();
        log.info("开始循环查询结果");
        for (QueryResult.Series series : seriesList) {
            if (series == null) {
                return;
            }
            log.info("结果数量为：" + (series.getValues() == null ?
                    0 : series.getValues().size()));
            log.info("colums ==>> {}" + series.getColumns());
            log.info("tags ==>> {}" + series.getTags());
            log.info("name ==>> {}" + series.getName());
            log.info("values ==>> {}" + series.getValues());
        }
    }

    /**
     * @description: 查询对象
     * @param
     * @return: java.util.List<com.example.entity.Temperature>
     * @author: guorf
     * @time: 2021/11/15 16:46
     */
    @GetMapping("/entity")
    public List<Temperature> entity() {
        log.info("---开始查询数据---");
        // InfluxDB支持分页查询,因此可以设置分页查询条件
        int page = 1; // 起始页（从0开始）
        int pageSize = 10;  // 每页条目数
        String pageQuery = " LIMIT " + pageSize + " OFFSET " + (page - 1) * pageSize;
        String queryCondition = "";  //查询条件暂且为空
        // 此处查询所有内容,如果
        String queryCmd = "SELECT time, machine, type, external, internal FROM "
                // 要指定从 RetentionPolicyName.measurement中查询指定数据,默认的策略可以不加；
                // + 策略name + "." + measurement
                + "temperature"
                // 添加查询条件(注意查询条件选择tag值,选择field数值会严重拖慢查询速度)
                + queryCondition
                // 查询结果需要按照时间排序
                + " ORDER BY time DESC"
                // 添加分页查询条件
                + pageQuery
                // 设置查询时区为（北京时间）
                + " tz('Asia/Shanghai')";

        // 开始查询
        QueryResult queryResult = influxDB.query(new Query(queryCmd, database),
                TimeUnit.MILLISECONDS);
        log.info("原始结果为：" + queryResult);

        // 获取查询结果
        List<QueryResult.Result> results = queryResult.getResults();
        if (results == null) {
            return null;
        }
        // 多个sql用分号隔开，因本次查询只有一个sql，所以取第一个就行
        QueryResult.Result result = results.get(0);
        List<QueryResult.Series> seriesList = result.getSeries();
        List<Temperature> temperatureList = new LinkedList<>();

        for (QueryResult.Series series : seriesList) {
            if (series == null) {
                return null;
            }
            log.info("结果数量为：" + (series.getValues() == null ?
                    0 : series.getValues().size()));
            log.info("colums ==>> " + series.getColumns());
            log.info("tags ==>> " + series.getTags());
            log.info("name ==>> " + series.getName());
            log.info("values ==>> " + series.getValues());

            series.getValues().forEach(valueData -> {
                Temperature temperature = new Temperature();
                // 直接查询出来的是科学计数法，需要转换为Long类型的数据
                BigDecimal decimalTime = new BigDecimal(valueData.get(0).toString());
                temperature.setTime(decimalTime.longValue());
                temperature.setMachine(valueData.get(1).toString());
                temperature.setType(valueData.get(2).toString());
                temperature.setExternal(Integer.valueOf(valueData.get(3).toString()));
                temperature.setInternal(Integer.valueOf(valueData.get(4).toString()));
                temperatureList.add(temperature);
            });
        }
        return temperatureList;
    }
}
