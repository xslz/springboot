package com.example.custom;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RocketMQMessageListener(topic = "transaction-message-topic", consumerGroup = "transaction-consumer-group")
public class TransactionMessageListener implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        log.info("接收到事务消息:{}", message);
    }
}
