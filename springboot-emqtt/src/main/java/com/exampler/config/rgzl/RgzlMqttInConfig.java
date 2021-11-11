package com.exampler.config.rgzl;

import com.exampler.receiver.rgzl.RgzlReceiver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

/**
 * @version 1.0.0
 * @description: 锐谷智联配置
 * @author: guorf
 * @time: 2021/11/3 11:53
 */
@Configuration
@ConditionalOnProperty(value = "mqtt.enabled", havingValue = "true")
public class RgzlMqttInConfig {

    private final RgzlReceiver rgzlReceiver;

    public RgzlMqttInConfig(RgzlReceiver rgzlReceiver) {
        this.rgzlReceiver = rgzlReceiver;
    }

    /**
     * @description: 属性订阅通道
     * @param
     * @return: MessageChannel
     * @author: guorf
     * @time: 2021/10/30 20:41
     */
    @Bean
    @Primary
    public MessageChannel rgzlInputChannel() {
        return new DirectChannel();
    }

    /**
     * @description: 属性上报入站消息处理工具，对于指定消息入站通道接收到生产者生产的消息后处理消息的工具。
     * @param
     * @return: org.springframework.messaging.MessageHandler
     * @author: guorf
     * @time: 2021/10/30 20:44
     */
    @Bean
    @ServiceActivator(inputChannel = "rgzlInputChannel")
    public MessageHandler rgzlMessageHandler() {
        return this.rgzlReceiver;
    }

    /**
     * @description: 属性上报消息生产者绑定通道
     * @param rgzlInputChannel
     * @param factory
     * @return: org.springframework.integration.core.MessageProducer
     * @author: guorf
     * @time: 2021/10/30 20:43
     */
    @Bean
    public MessageProducer rgzlChannelInbound(MessageChannel rgzlInputChannel, MqttPahoClientFactory factory) {
        String clientId = "localhost-" + System.currentTimeMillis();
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter("localhost", factory, "/+/+/+/thing/event/property/post","/+/+/+/thing/downlink/reply/message", "$SYS/brokers/+/clients/#");
//        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter("localhost", factory, "$SYS/brokers/+/clients/#");
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(0);
        adapter.setOutputChannel(rgzlInputChannel);
        return adapter;
    }
}
