package com.exampler.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.exampler.sender.MqttMessageSender;
import com.exampler.utils.JSONResult;
import com.exampler.vo.RgzlVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @version 1.0.0
 * @description:
 * @author: guorf
 * @time: 2021/10/30 21:49
 */
@Slf4j
@RestController
@ConditionalOnProperty(value = "mqtt.enabled", havingValue = "true")
@RequestMapping("/public")
public class MqttController {
    private final MqttMessageSender mqttMessageSender;

    public MqttController(MqttMessageSender mqttMessageSender) {
        this.mqttMessageSender = mqttMessageSender;
    }

    /**
     * 发送mqtt消息
     *
     * @param topic 消息主题
     * @param data  消息内容
     * @return
     */
    @PostMapping("/send")
    public JSONResult send(String topic, String data) {
        log.info("开始发送mqtt消息,主题：{},消息：{}", topic, data);
        if (StrUtil.isNotBlank(topic)) {
            this.mqttMessageSender.sendToMqtt(topic, data);
        } else {
            this.mqttMessageSender.sendToMqtt(data);
        }
        log.info("发送mqtt消息完成,主题：{},消息：{}", topic, data);
        return JSONResult.ok();
    }

    /**
     * 锐谷智联发送mqtt消息
     *
     * @param sn 网关SN号
     * @param pk 物模型key
     * @param dn 设备名
     * @param data  消息内容
     * @return
     */
    @PostMapping("/rgzl/st/send")
    public JSONResult send(String sn, String pk, String dn, String data) {
        String topicD = "/${sn}/${pk}/${dn}/thing/property/downlink";
        String topic = topicD.replace("${sn}", sn).replace("${pk}", pk).replace("${dn}", dn);
        Map<String, Object> message = new HashMap<>();
        message.put("id", new Date().getTime()+"");
        message.put("version", "1.0");
        message.put("productKey", pk);
        message.put("deviceName", dn);
        message.put("method", "thing.service.property.set");
        message.put("params", data);
        this.mqttMessageSender.sendToMqtt(topic, JSONUtil.toJsonStr(message));
        log.info("开始发送mqtt消息,主题：{},消息：{}", topic, JSONUtil.toJsonStr(message));
        log.info("发送mqtt消息完成,主题：{},消息：{}", topic, JSONUtil.toJsonStr(message));
        return JSONResult.ok();
    }

    /**
     * 锐谷智联发送mqtt消息
     *
     * @param rgzlVo  消息内容
     * @return
     */
    @PostMapping("/rgzl/ob/send")
    public JSONResult send(@RequestBody RgzlVo rgzlVo) {
        String topicD = "/${sn}/${pk}/${dn}/thing/property/downlink";
        String topic = topicD.replace("${sn}", rgzlVo.getSn()).replace("${pk}", rgzlVo.getProductKey()).replace("${dn}", rgzlVo.getDeviceName());
        JSONObject jsonObject = JSONUtil.parseObj(rgzlVo);
        jsonObject.putOnce("id", new Date().getTime()+"");
        jsonObject.putOnce("version", "1.0");
        jsonObject.putOnce("method", "thing.service.property.set");
        jsonObject.remove("sn");
        this.mqttMessageSender.sendToMqtt(topic, jsonObject.toString());
        log.info("开始发送mqtt消息,主题：{},消息：{}", topic, jsonObject.toString());
        log.info("发送mqtt消息完成,主题：{},消息：{}", topic, jsonObject.toString());
        return JSONResult.ok();
    }
}
