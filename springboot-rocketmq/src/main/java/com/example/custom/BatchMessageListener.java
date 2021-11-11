package com.example.custom;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RocketMQMessageListener(topic = "batch-message-topic", consumerGroup = "batch-consumer-group")
public class BatchMessageListener implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        log.info("接收到批量消息:{}", message);
    }
}
