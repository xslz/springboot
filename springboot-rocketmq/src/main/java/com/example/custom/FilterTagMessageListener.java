package com.example.custom;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * tag过滤
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = "filter-message-topic", consumerGroup = "filter-tag-consumer-group", selectorExpression = "sync-tags || async-tags")
public class FilterTagMessageListener implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        log.info("接收到TAG过滤消息为:{}", message);
    }
}
