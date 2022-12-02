package com.project.iot_mqtt.Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "test", schema = "iot_mqtt", catalog = "")
public class TestEntity {
    @Basic
    @Column(name = "topic")
    private String topic;
    @Basic
    @Column(name = "message")
    private String message;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestEntity that = (TestEntity) o;
        return id == that.id && Objects.equals(topic, that.topic) && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(topic, message, id);
    }
}
