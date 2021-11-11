package com.example.service.impl;

import com.example.service.OrderMessageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class OrderMessageServiceImpl implements OrderMessageService {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 发送同步顺序消息
     * @param id
     * @param message
     */
    @Override
    public void sendSyncOrderMessage(String id, String message) {
        Message<String> strMessage = MessageBuilder.withPayload(message).setHeader(RocketMQHeaders.KEYS, id).build();
        rocketMQTemplate.setMessageQueueSelector(new MessageQueueSelector() {
            @Override
            public MessageQueue select(List<MessageQueue> list, org.apache.rocketmq.common.message.Message message, Object obj) {
                Integer uid = Integer.valueOf(String.valueOf(obj));
                int index = uid % list.size();
                return list.get(index);
            }
        });
        SendResult result = rocketMQTemplate.syncSendOrderly("order-message-topic:sync-tags", strMessage, id);
        if (result.getSendStatus() == SendStatus.SEND_OK) {
            log.info("发送同步顺序消息成功!");
        } else {
            log.error("发送同步顺序消息失败!消息ID为:{}", result.getMsgId());
        }

    }

    /**
     * 发送异步顺序消息
     * @param id
     * @param message
     */
    @Override
    public void sendAsyncOrderMessage(String id, String message) {
        Message<String> strMessage = MessageBuilder.withPayload(message).setHeader(RocketMQHeaders.KEYS, id).build();
        rocketMQTemplate.setMessageQueueSelector(new MessageQueueSelector() {
            @Override
            public MessageQueue select(List<MessageQueue> list, org.apache.rocketmq.common.message.Message message, Object obj) {
                Integer uid = Integer.valueOf(String.valueOf(obj));
                int index = uid % list.size();
                return list.get(index);
            }
        });
        rocketMQTemplate.asyncSendOrderly("order-message-topic:async-tags", strMessage, id, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                if (sendResult.getSendStatus() == SendStatus.SEND_OK) {
                    log.info("发送异步顺序消息成功!消息ID为:{}", sendResult.getMsgId());
                }
            }
            @Override
            public void onException(Throwable throwable) {
                log.info("发送异步顺序消息失败!失败原因为:{}", throwable.getMessage());
            }
        });
    }

    /**
     * 发送单向顺序消息
     * @param id
     * @param message
     */
    @Override
    public void sendOnewayOrderMessage(String id, String message) {
        Message<String> strMessage = MessageBuilder.withPayload(message).setHeader(RocketMQHeaders.KEYS, id).build();
        rocketMQTemplate.setMessageQueueSelector(new MessageQueueSelector() {
            @Override
            public MessageQueue select(List<MessageQueue> list, org.apache.rocketmq.common.message.Message message, Object obj) {
                Integer uid = Integer.valueOf(String.valueOf(obj));
                int index = uid % list.size();
                return list.get(index);
            }
        });
        rocketMQTemplate.sendOneWayOrderly("order-message-topic:oneway-tags", strMessage, id);
    }
}
