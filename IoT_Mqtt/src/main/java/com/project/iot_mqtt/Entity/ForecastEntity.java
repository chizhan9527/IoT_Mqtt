package com.project.iot_mqtt.Entity;

import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.Date;

public class ForecastEntity {
    public Date date;
    public Object sum;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
}
