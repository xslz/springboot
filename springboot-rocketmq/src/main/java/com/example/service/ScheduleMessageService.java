package com.example.service;

/**
 * 延迟消息接口
 */
public interface ScheduleMessageService {
    /**
     * 发送同步定时消息
     * @param id
     * @param message 消息内容
     * @param timeout 过期时间(毫秒)
     * @param delayLevel 延时级别为(1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h)其下标，从1开始
     */
    void sendSyncScheduleMessage(String id, String message, long timeout, int delayLevel);
}
