package com.project.iot_mqtt.controller;

import com.project.iot_mqtt.Entity.MyMessage;
import com.project.iot_mqtt.config.MqttConfig;
import com.project.iot_mqtt.service.MqttGateway;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class MqttController {

    @Resource
    private MqttGateway mqttGateway;

    @Resource
    private MqttConfig mqttConfig;

    @PostMapping("/send")
    public String send(@RequestBody MyMessage myMessage) {
        // 发送消息到指定主题
        mqttGateway.sendToMqtt(myMessage.getTopic(), 1, myMessage.getContent());
        return "send topic: " + myMessage.getTopic()+ ", message : " + myMessage.getContent();
    }

    @PutMapping("/topic")
    public String addTopic(@RequestParam("topic")String topic){
        mqttConfig.mqttSubscriber().addTopic(topic,1);
        return "ok";
    }

}

