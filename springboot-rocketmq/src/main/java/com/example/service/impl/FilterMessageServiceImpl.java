package com.example.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.service.FilterMessageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FilterMessageServiceImpl implements FilterMessageService {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Override
    public void sendFilterTagMessage(String id, String message) {
        Message<String> strMessage = MessageBuilder.withPayload(message).setHeader(RocketMQHeaders.KEYS, id).build();
        SendResult result = rocketMQTemplate.syncSend("filter-message-topic:sync-tags", strMessage);
        if (result.getSendStatus() == SendStatus.SEND_OK) {
            log.info("发送TAG过滤消息成功!");
        } else {
            log.info("发送TAG过滤消息失败!消息状态为:{}", result.getSendStatus());
        }
    }

    @Override
    public void sendFilterSqlMessage(String id, String message, int index) {
        Message<String> strMessage = MessageBuilder.withPayload(message)
                .setHeader(RocketMQHeaders.KEYS, id)
                .setHeader("age", index).build();
        SendResult result = rocketMQTemplate.syncSend("filter-message-topic:sync-tags", strMessage);
        if (result.getSendStatus() == SendStatus.SEND_OK) {
            log.info("发送SQL过滤消息成功!");
        } else {
            log.info("发送SQL过滤消息失败!消息状态为:{}", result.getSendStatus());
        }
    }
}
