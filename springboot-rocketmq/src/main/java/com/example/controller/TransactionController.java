package com.example.controller;

import com.alibaba.fastjson.JSON;
import com.example.dto.User;
import com.example.service.TransactionMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
@RestController
@RequestMapping("transaction")
public class TransactionController {

    @Autowired
    private TransactionMessageService transactionMessageService;
    /**
     * 事务消息
     */
    @GetMapping("")
    public void transactionMessage() {
        User user = new User();
        user.setName("测试");
        user.setAge(20);
        transactionMessageService.sendTransactionMessage(new Date().getTime()+"", JSON.toJSONString(user));
    }
}
