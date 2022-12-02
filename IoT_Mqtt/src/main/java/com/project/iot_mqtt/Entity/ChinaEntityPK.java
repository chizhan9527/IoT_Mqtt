package com.project.iot_mqtt.Entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class ChinaEntityPK implements Serializable {
    @Column(name = "province_name")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String provinceName;
    @Column(name = "update_time")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Timestamp updateTime;
    @Column(name = "city_name")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String cityName;

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChinaEntityPK that = (ChinaEntityPK) o;
        return Objects.equals(provinceName, that.provinceName) && Objects.equals(updateTime, that.updateTime) && Objects.equals(cityName, that.cityName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(provinceName, updateTime, cityName);
    }
}
