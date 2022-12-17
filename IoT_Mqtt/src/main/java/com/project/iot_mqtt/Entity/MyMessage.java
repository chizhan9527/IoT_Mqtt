package com.project.iot_mqtt.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyMessage {
    private String topic;
    private Object content;

    public static MyMessage success(){
        MyMessage myMessage = new MyMessage();
        myMessage.setTopic("test/");
        myMessage.setContent(null);
        return myMessage;
    }
    public static MyMessage success(String topic,Object content){
        MyMessage myMessage = new MyMessage();
        myMessage.setTopic(topic);
        myMessage.setContent(content);
        return myMessage;
    }

    public static MyMessage success(Object content){
        MyMessage myMessage = new MyMessage();
        myMessage.setTopic("test/");
        myMessage.setContent(content);
        return myMessage;
    }

}

