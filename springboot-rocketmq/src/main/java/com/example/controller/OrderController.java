package com.example.controller;

import cn.hutool.json.JSONObject;
import com.example.service.OrderMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试顺序消息
 */
@Slf4j
@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderMessageService orderMessageService;

    /**
     * 同步顺序消息
     */
    @GetMapping("/sync")
    public void sync(){
        for (int i = 1; i < 5; i++) {
            orderMessageService.sendSyncOrderMessage(String.valueOf(i), "hello sync" + i);
        }
    }

    /**
     * 异步顺序消息
     */
    @GetMapping("/async")
    public void async(){
        for (int i = 1; i < 5; i++) {
            orderMessageService.sendAsyncOrderMessage(String.valueOf(i), "hello async" + i);
        }
    }

    /**
     * 单向顺序消息
     */
    @GetMapping("/oneway")
    public void oneway(){
        for (int i = 1; i < 5; i++) {
            orderMessageService.sendOnewayOrderMessage(String.valueOf(i), "hello oneway" + i);
        }
    }
}
