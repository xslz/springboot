package com.example.service.impl;

import com.example.service.BatchMessageService;
import com.example.utils.ListSplitter;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BatchMessageServiceImpl implements BatchMessageService {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;


    @Override
    public void sendBatchMessage(List<Message<String>> messageList) {
        SendResult result = rocketMQTemplate.syncSend("batch-message-topic:sync-tags", messageList);
        if (result.getSendStatus() == SendStatus.SEND_OK) {
            log.info("发送批量消息成功!消息ID为:{}", result.getMsgId());
        } else {
            log.info("发送批量消息失败!消息ID为:{},消息状态为:{}", result.getMsgId(), result.getSendStatus());
        }
//        //限制数据大小
//        ListSplitter splitter = new ListSplitter(1024 * 1024 * 1, messageList);
//        while (splitter.hasNext()) {
//            List<Message<String>> nextList = splitter.next();
//            SendResult result = rocketMQTemplate.syncSend("batch-message-topic:sync-tags", nextList);
//            if (result.getSendStatus() == SendStatus.SEND_OK) {
//                log.info("发送批量消息成功!消息ID为:{}", result.getMsgId());
//            } else {
//                log.info("发送批量消息失败!消息ID为:{},消息状态为:{}", result.getMsgId(), result.getSendStatus());
//            }
//        }
    }
}
