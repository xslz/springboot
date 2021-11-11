package com.example.controller;

import com.example.service.BatchMessageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 发送批量消息
 */
@Slf4j
@RestController
@RequestMapping("batch")
public class BatchContreller {

    @Autowired
    private BatchMessageService batchMessageService;

    @GetMapping("")
    public void batchMessage() {
        List<Message<String>> messageList = new ArrayList<Message<String>>();
        for (int i = 0; i < 5; i++) {
            String uuid = UUID.randomUUID().toString();
            Message<String> message = MessageBuilder.withPayload("hello" + i).setHeader(RocketMQHeaders.KEYS, uuid).build();
            messageList.add(message);
        }
        batchMessageService.sendBatchMessage(messageList);
    }
}
