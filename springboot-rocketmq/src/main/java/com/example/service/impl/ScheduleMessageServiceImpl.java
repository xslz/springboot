package com.example.service.impl;

import com.example.service.ScheduleMessageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class ScheduleMessageServiceImpl implements ScheduleMessageService {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Override
    public void sendSyncScheduleMessage(String id, String message, long timeout, int delayLevel) {
        Message<String> strMessage = MessageBuilder.withPayload(message).setHeader(RocketMQHeaders.KEYS, id).build();
        SendResult result = rocketMQTemplate.syncSend("schedule-message-topic:sync-tags", strMessage, timeout, delayLevel);
        if (result.getSendStatus() == SendStatus.SEND_OK) {
            log.info("发送同步定时消息成功!消息ID为:{},当前时间为:{}", result.getMsgId(), LocalDateTime.now());
        } else {
            log.info("发送同步定时消息失败!消息ID为:{}", result.getMsgId());
        }
    }
}
