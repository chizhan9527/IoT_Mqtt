package com.project.iot_mqtt.dao;

import com.project.iot_mqtt.Entity.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface testDao extends JpaRepository<TestEntity,Integer> {

}
