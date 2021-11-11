package com.example.controller;

import cn.hutool.json.JSONObject;
import com.example.service.SimpleMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 测试简单消息
 */
@RestController
@RequestMapping("simple")
public class SimpleController {

    @Autowired
    private SimpleMessageService simpleMessageService;

    /**
     * 发送消息
     */
    @GetMapping("")
    public void simple(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.putOnce("qos", "2");
        jsonObject.putOnce("productKey", "lgYlF5HPbRm");
//        simpleMessageService.sendMessage("这是测试");
        simpleMessageService.sendMessage(jsonObject.toString());
    }

    /**
     * 发送同步消息
     */
    @GetMapping("/sync")
    public void simpleSync(){
        simpleMessageService.sendSyncMessage(new Date().getTime()+"", "这是同步消息");
    }

    /**
     * 发送异步消息
     */
    @GetMapping("/async")
    public void simpleAsync(){
        simpleMessageService.sendAsyncMessage(new Date().getTime()+"", "这是异步消息");
    }

    /**
     * 发送单向消息
     */
    @GetMapping("/oneway")
    public void simpleOneway(){
        simpleMessageService.sendOnewayMessage(new Date().getTime()+"", "这是单向消息");
    }
}
