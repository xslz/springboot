package com.example.custom;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * sql过滤
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = "filter-message-topic", consumerGroup = "filter-sql-consumer-group", selectorExpression = "age >= 5", selectorType = SelectorType.SQL92)
public class FilterSqlMessageListener implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        log.info("接收到SQL过滤消息为:{}", message);
    }
}
