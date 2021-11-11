package com.example.service;

/**
 * 有序消息
 */
public interface OrderMessageService {
    /**
     * 发送同步顺序消息
     * @param id
     * @param message
     */
    void sendSyncOrderMessage(String id, String message);

    /**
     * 发送异步顺序消息
     * @param id
     * @param message
     */
    void sendAsyncOrderMessage(String id, String message);

    /**
     * 发送单向顺序消息
     * @param id
     * @param message
     */
    void sendOnewayOrderMessage(String id, String message);
}
