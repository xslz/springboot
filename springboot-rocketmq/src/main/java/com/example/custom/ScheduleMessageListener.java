package com.example.custom;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RocketMQMessageListener(topic = "schedule-message-topic", consumerGroup = "schedule-consumer-group")
public class ScheduleMessageListener implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        log.info("接收到定时消息:{},当前时间为:{}", message, LocalDateTime.now());
    }
}
