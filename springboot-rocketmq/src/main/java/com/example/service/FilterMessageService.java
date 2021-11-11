package com.example.service;

/**
 * 消息过滤
 */
public interface FilterMessageService {
    /**
     * 发送Tag过滤消息
     * @param id
     * @param message
     */
    void sendFilterTagMessage(String id, String message);

    /**
     * 发送SQL过滤消息
     * @param id
     * @param message
     * @param index
     */
    void sendFilterSqlMessage(String id, String message, int index);
}
