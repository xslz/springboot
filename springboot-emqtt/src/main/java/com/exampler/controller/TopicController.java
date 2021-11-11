package com.exampler.controller;

import com.exampler.utils.JSONResult;
import com.exampler.vo.TopicVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @version 1.0.0
 * @description: 主题处理
 * @author: guorf
 * @time: 2021/11/1 23:26
 */
@Slf4j
@RestController
@RequestMapping("/topic")
public class TopicController {

//    @Autowired
//    private DynamicMqttInConfig dynamicMqttInConfig;

    /**
     * @description: 添加主题
     * @param topicVo
     * @return: com.exampler.utils.JSONResult
     * @author: guorf
     * @time: 2021/11/1 23:37
     */
    @PostMapping("/add")
    public JSONResult addTopic(@RequestBody TopicVo topicVo){
//        dynamicMqttInConfig.addListenTopic(topicVo);
        return JSONResult.ok();
    }

    /**
     * @description: 添加主题
     * @param topicVo
     * @return: com.exampler.utils.JSONResult
     * @author: guorf
     * @time: 2021/11/1 23:37
     */
    @PostMapping("/adds")
    public JSONResult addTopics(@RequestBody List<TopicVo> topicVo){
//        dynamicMqttInConfig.addListenTopic(topicVo);
        return JSONResult.ok();
    }
    /**
     * @description: 删除默认客户端订阅的主题
     * @param topic
     * @return: com.exampler.utils.JSONResult
     * @author: guorf
     * @time: 2021/11/1 23:40
     */
    @DeleteMapping("/remove/{topic}")
    public JSONResult removeTopic(@PathVariable(value = "topic") String topic) {
//        dynamicMqttInConfig.removeListenTopic(topic);
        return JSONResult.ok();
    }

    /**
     * @description: 删除指定客户端订阅的主题
     * @param clientId
     * @param topic
     * @return: com.exampler.utils.JSONResult
     * @author: guorf
     * @time: 2021/11/1 23:41
     */
    @DeleteMapping("/remove/{clientId}/{topic}")
    public JSONResult removeTopic(@PathVariable(value = "clientId") String clientId, @PathVariable(value = "topic") String topic) {
//        dynamicMqttInConfig.removeListenTopic(clientId, topic);
        return JSONResult.ok();
    }
}
