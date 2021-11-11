package com.example.service;

public interface TransactionMessageService {
    /**
     * 发送事务消息
     * @param id
     * @param message
     */
    void sendTransactionMessage(String id, String message);
}
