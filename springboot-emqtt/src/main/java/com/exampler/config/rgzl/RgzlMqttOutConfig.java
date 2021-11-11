package com.exampler.config.rgzl;

import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

/**
 * @version 1.0.0
 * @description: mqtt出站消息处理配置
 * @author: guorf
 * @time: 2021/10/30 21:08
 */
//@Configuration
//@ConditionalOnProperty(value = "mqtt.enabled", havingValue = "true")
public class RgzlMqttOutConfig {

    /**
     * mqtt消息出站通道，用于发送出站消息
     *
     * @return
     */
    @Bean
    public MessageChannel downlinkChannel() {
        return new DirectChannel();
    }

    /**
     * mqtt消息出站通道默认配置，用于向外发出mqtt消息，当前案例中发到了同一个mqtt服务器
     *
     * @return
     */
    @Bean
    @ServiceActivator(inputChannel = "downlinkChannel")
    public MessageHandler downlinkOutbound(MqttPahoClientFactory factory) {
//        String clientId = "localhost-" + System.currentTimeMillis();
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler("localhost", factory);
        messageHandler.setAsync(true);
        messageHandler.setDefaultQos(0);
        messageHandler.setDefaultTopic("/thing/property/downlink");
        return messageHandler;
    }
}
