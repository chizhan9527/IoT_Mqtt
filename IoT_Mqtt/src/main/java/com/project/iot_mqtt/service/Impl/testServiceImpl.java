package com.project.iot_mqtt.service.Impl;

import com.project.iot_mqtt.Entity.TestEntity;
import com.project.iot_mqtt.service.testService;
import org.springframework.beans.factory.annotation.Autowired;
import com.project.iot_mqtt.dao.testDao;
import org.springframework.stereotype.Service;

@Service
public class testServiceImpl implements testService {

    @Autowired
    private testDao testDao;
    @Override
    public String addTest(TestEntity testEntity) {
        testDao.save(testEntity);
        return "ok";
    }
}
