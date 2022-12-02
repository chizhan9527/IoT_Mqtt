package com.project.iot_mqtt.config;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

import java.util.logging.Logger;

@Configuration
@Slf4j
public class MqttConfig {

    private final String clientId = "mqtt_114514";
    /**
     * 1、先创建连接
     * 创建MqttPahoClientFactory，设置MQTT Broker连接属性，如果使用SSL验证，也在这里设置。
     * @return factory
     */
    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();

        MqttConnectOptions options = new MqttConnectOptions();
        // 设置代理端的URL地址，可以是多个
        options.setServerURIs(new String[]{"tcp://123.60.110.3:1883"});
        options.setUserName("lilei");
        String passWord = "123456";
        options.setPassword(passWord.toCharArray());

        factory.setConnectionOptions(options);
        return factory;
    }

    /**
     * 2、入站通道（生产者）
     */
    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    /**
     * 入站
     * 配置client,监听的topic
     * MQTT消息订阅绑定（消费者）
     */
    @Bean
    public MqttPahoMessageDrivenChannelAdapter mqttSubscriber() {
        String [] topics = new String[]{"publish", "test/", "test"};
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(clientId+"_subscribe",
                mqttClientFactory(), topics);
        adapter.setCompletionTimeout(5000);
        // Paho消息转换器
        DefaultPahoMessageConverter defaultPahoMessageConverter = new DefaultPahoMessageConverter();
        // 按字节接收消息
        //defaultPahoMessageConverter.setPayloadAsBytes(true);
        adapter.setConverter(defaultPahoMessageConverter);
        adapter.setQos(1);
        adapter.setOutputChannel(mqttInputChannel());
        return adapter;
    }

    @Bean
    public MessageProducer inbound() {
        return mqttSubscriber();
    }


    /**
     * 3、消息转化，中间站
     */

    @Bean
    // ServiceActivator注解表明：当前方法用于处理MQTT消息，inputChannel参数指定了用于消费消息的channel。
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler handler() {
        return new MessageHandler() {
            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                try{
                    String topic = message.getHeaders().get("mqtt_receivedTopic").toString();
                    String qos = message.getHeaders().get("mqtt_receivedQos").toString();
                    String payload = message.getPayload().toString();
                    log.info("主题: "+topic);
                    log.info("内容："+payload);
                    log.info("级别："+qos);
                }catch(Exception e){
                    log.error(e.getMessage(),e);
                }
            }
        };
    }


    /**
     * 4、消息出去
     */

    /**
     * 出站通道
     */
    @Bean
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }

    /**
     * 出站
     */
    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    public MessageHandler outbound() {
        // 发送消息和消费消息Channel可以使用相同MqttPahoClientFactory
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(clientId+"_publish", mqttClientFactory());
        messageHandler.setAsync(true); // 如果设置成true，即异步，发送消息时将不会阻塞。
        messageHandler.setDefaultTopic("command");
        messageHandler.setDefaultQos(1); // 设置默认QoS

        // Paho消息转换器
        DefaultPahoMessageConverter defaultPahoMessageConverter = new DefaultPahoMessageConverter();

        // defaultPahoMessageConverter.setPayloadAsBytes(true); // 发送默认按字节类型发送消息
        messageHandler.setConverter(defaultPahoMessageConverter);
        return messageHandler;
    }

}

