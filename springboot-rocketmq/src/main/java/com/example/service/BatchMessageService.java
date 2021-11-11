package com.example.service;

import org.springframework.messaging.Message;

import java.util.List;

/**
 * 发送批量消息
 */
public interface BatchMessageService {

    /**
     * 发送批量消息
     * @param messageList
     */
    void sendBatchMessage(List<Message<String>> messageList);
}
