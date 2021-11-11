package com.example.controller;

import com.example.service.ScheduleMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * 延迟消息
 */
@Slf4j
@RestController
@RequestMapping("schedule")
public class ScheduleController {

    @Autowired
    private ScheduleMessageService scheduleMessageService;

    @GetMapping("")
    public void scheduleMessage() {
        String uuid = UUID.randomUUID().toString();
//        1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
        scheduleMessageService.sendSyncScheduleMessage(uuid, "hello schedule" + uuid, 100,3);
    }


}
