package com.example.service.impl;

import com.example.service.TransactionMessageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TransactionMessageServiceImpl implements TransactionMessageService {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Override
    public void sendTransactionMessage(String id, String message) {
        Message<String> strMessage = MessageBuilder.withPayload(message).setHeader(RocketMQHeaders.KEYS, id).build();
        TransactionSendResult result = rocketMQTemplate.sendMessageInTransaction("transaction-message-topic:transaction-tags", strMessage, id);
        if (result.getSendStatus() == SendStatus.SEND_OK) {
            log.info("发送事务消息成功!消息ID为:{}", result.getMsgId());
        }
    }
}
