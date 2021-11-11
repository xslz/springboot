package com.example.custom;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RocketMQMessageListener(topic = "order-message-topic", consumerGroup = "order-consumer-group", consumeMode = ConsumeMode.ORDERLY)
public class OrderMessageListener implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        log.info("接收到顺序消息为:{}", message);
    }
}
