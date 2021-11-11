package com.example.controller;

import com.example.service.FilterMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * 过滤消息
 */
@Slf4j
@RestController
@RequestMapping("filter")
public class FilterController {

    @Autowired
    private FilterMessageService filterMessageService;

    /**
     * tag过滤
     */
    @GetMapping("/tag")
    public void filterTagMessage() {
        String uuid = UUID.randomUUID().toString();
        filterMessageService.sendFilterTagMessage(uuid, "hello");
    }

    /**
     * sql过滤
     */
    @GetMapping("/sql")
    public void filterSqlMessage() {
        for (int i = 0; i < 10; i++) {
            String uuid = UUID.randomUUID().toString();
            filterMessageService.sendFilterSqlMessage(uuid, "hello" + i, i);
        }
    }
}
