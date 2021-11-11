package com.exampler.receiver.rgzl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

/**
 * @version 1.0.0
 * @description:mqtt 属性上报
 * @author: guorf
 * @time: 2021/10/30 21:40
 */
@Slf4j
@Component
@ConditionalOnProperty(value = "mqtt.enabled", havingValue = "true")
public class RgzlReceiver implements MessageHandler {

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        String topic = String.valueOf(message.getHeaders().get(MqttHeaders.RECEIVED_TOPIC));
        String payload = String.valueOf(message.getPayload());
        log.info("topic is {}", topic);
        log.info("message is {}", message.getHeaders());
        JSONObject jsonObject = JSONUtil.parseObj(payload);
        if (topic.endsWith("/connected")) {
            log.info("【客户端上线】clientid：{}, ip: {}的客户端上线", jsonObject.get("clientid"), jsonObject.get("ipaddress"));
        } else if (topic.endsWith("/disconnected")) {
            log.info("【客户端下线】clientid：{}, ip: {}的客户端下线", jsonObject.get("clientid"), jsonObject.get("ipaddress"));
        } else if (topic.endsWith("thing/event/property/post")) {
            log.info("【属性上报】接收到mqtt消息，主题:{} 消息:{}", topic, payload);
        } else if(topic.endsWith("thing/downlink/reply/message")) {
            log.info("【指令下行应答】接收到 mqtt消息，主题:{} 消息:{}", topic, payload);
        }

    }
}
