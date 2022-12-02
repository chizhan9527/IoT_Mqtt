package com.project.iot_mqtt.Entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class ForeignEntityPK implements Serializable {
    @Column(name = "country_name")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String countryName;
    @Column(name = "update_time")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Timestamp updateTime;

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ForeignEntityPK that = (ForeignEntityPK) o;
        return Objects.equals(countryName, that.countryName) && Objects.equals(updateTime, that.updateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countryName, updateTime);
    }
}
