package com.example.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.service.SimpleMessageService;
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

/**
 * 普通消息
 */
@Slf4j
@Service
public class SimpleMessageServiceImpl implements SimpleMessageService {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 发送消息
     * @param message
     */
    @Override
    public void sendMessage(String message) {
        rocketMQTemplate.convertAndSend("simple-message-topic", message);
        log.info("发送消息成功!");
    }

    /**
     * 发送同步消息
     * @param id
     * @param message
     */
    @Override
    public void sendSyncMessage(String id, String message) {
        Message<String> strMessage = MessageBuilder.withPayload(message).setHeader(RocketMQHeaders.KEYS, id).build();
        SendResult result = rocketMQTemplate.syncSend("simple-message-topic:sync-tags", strMessage);
        log.info("发送简单同步消息成功!返回信息为:{}", JSON.toJSONString(result));

    }

    /**
     * 发送异步消息
     * @param id
     * @param message
     */
    @Override
    public void sendAsyncMessage(String id, String message) {
        Message<String> strMessage = MessageBuilder.withPayload(message).setHeader(RocketMQHeaders.KEYS, id).build();
        rocketMQTemplate.asyncSend("simple-message-topic:async-tags", strMessage, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                if (sendResult.getSendStatus() == SendStatus.SEND_OK) {
                    log.info("发送简单异步消息成功!返回信息为:{}", JSON.toJSONString(sendResult));
                }
            }
            @Override
            public void onException(Throwable throwable) {
                log.error("发送简单异步消息失败!异常信息为:{}", throwable.getMessage());
            }
        });
    }

    /**
     * 发送单向消息
     * @param id
     * @param message
     */
    @Override
    public void sendOnewayMessage(String id, String message) {
        Message<String> strMessage = MessageBuilder.withPayload(message).setHeader(RocketMQHeaders.KEYS, id).build();
        rocketMQTemplate.sendOneWay("simple-message-topic:oneway-tags", strMessage);
    }
}
