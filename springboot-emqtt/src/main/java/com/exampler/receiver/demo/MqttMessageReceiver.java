package com.exampler.receiver.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

/**
 * @version 1.0.0
 * @description:mqtt订阅消息处理
 * @author: guorf
 * @time: 2021/10/30 21:40
 */
@Slf4j
@Component
@ConditionalOnProperty(value = "mqtt.enabled", havingValue = "true")
public class MqttMessageReceiver implements MessageHandler {

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        String topic = String.valueOf(message.getHeaders().get(MqttHeaders.RECEIVED_TOPIC));
        String payload = String.valueOf(message.getPayload());
        log.info("接收到 mqtt消息，主题:{} 消息:{}", topic, payload);
    }
}
