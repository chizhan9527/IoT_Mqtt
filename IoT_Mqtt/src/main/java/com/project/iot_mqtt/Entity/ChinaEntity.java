package com.project.iot_mqtt.Entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "china", schema = "iot_mqtt", catalog = "")
@IdClass(ChinaEntityPK.class)
public class ChinaEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "province_name")
    private String provinceName;
    @Basic
    @Column(name = "province_english_name")
    private String provinceEnglishName;
    @Basic
    @Column(name = "province_zigcode")
    private Integer provinceZigcode;
    @Basic
    @Column(name = "province_confirm_count")
    private Integer provinceConfirmCount;
    @Basic
    @Column(name = "province_suspected_count")
    private Integer provinceSuspectedCount;
    @Basic
    @Column(name = "province_cured_count")
    private Integer provinceCuredCount;
    @Basic
    @Column(name = "province_dead_count")
    private Integer provinceDeadCount;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "update_time")
    private Timestamp updateTime;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "city_name")
    private String cityName;
    @Basic
    @Column(name = "city_english_name")
    private String cityEnglishName;
    @Basic
    @Column(name = "city_zigcode")
    private Integer cityZigcode;
    @Basic
    @Column(name = "city_confirm_count")
    private Integer cityConfirmCount;
    @Basic
    @Column(name = "city_suspected_count")
    private Integer citySuspectedCount;
    @Basic
    @Column(name = "city_cured_count")
    private Integer cityCuredCount;
    @Basic
    @Column(name = "city_dead_count")
    private Integer cityDeadCount;

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getProvinceEnglishName() {
        return provinceEnglishName;
    }

    public void setProvinceEnglishName(String provinceEnglishName) {
        this.provinceEnglishName = provinceEnglishName;
    }

    public Integer getProvinceZigcode() {
        return provinceZigcode;
    }

    public void setProvinceZigcode(Integer provinceZigcode) {
        this.provinceZigcode = provinceZigcode;
    }

    public Integer getProvinceConfirmCount() {
        return provinceConfirmCount;
    }

    public void setProvinceConfirmCount(Integer provinceConfirmCount) {
        this.provinceConfirmCount = provinceConfirmCount;
    }

    public Integer getProvinceSuspectedCount() {
        return provinceSuspectedCount;
    }

    public void setProvinceSuspectedCount(Integer provinceSuspectedCount) {
        this.provinceSuspectedCount = provinceSuspectedCount;
    }

    public Integer getProvinceCuredCount() {
        return provinceCuredCount;
    }

    public void setProvinceCuredCount(Integer provinceCuredCount) {
        this.provinceCuredCount = provinceCuredCount;
    }

    public Integer getProvinceDeadCount() {
        return provinceDeadCount;
    }

    public void setProvinceDeadCount(Integer provinceDeadCount) {
        this.provinceDeadCount = provinceDeadCount;
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

    public String getCityEnglishName() {
        return cityEnglishName;
    }

    public void setCityEnglishName(String cityEnglishName) {
        this.cityEnglishName = cityEnglishName;
    }

    public Integer getCityZigcode() {
        return cityZigcode;
    }

    public void setCityZigcode(Integer cityZigcode) {
        this.cityZigcode = cityZigcode;
    }

    public Integer getCityConfirmCount() {
        return cityConfirmCount;
    }

    public void setCityConfirmCount(Integer cityConfirmCount) {
        this.cityConfirmCount = cityConfirmCount;
    }

    public Integer getCitySuspectedCount() {
        return citySuspectedCount;
    }

    public void setCitySuspectedCount(Integer citySuspectedCount) {
        this.citySuspectedCount = citySuspectedCount;
    }

    public Integer getCityCuredCount() {
        return cityCuredCount;
    }

    public void setCityCuredCount(Integer cityCuredCount) {
        this.cityCuredCount = cityCuredCount;
    }

    public Integer getCityDeadCount() {
        return cityDeadCount;
    }

    public void setCityDeadCount(Integer cityDeadCount) {
        this.cityDeadCount = cityDeadCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChinaEntity that = (ChinaEntity) o;
        return Objects.equals(provinceName, that.provinceName) && Objects.equals(provinceEnglishName, that.provinceEnglishName) && Objects.equals(provinceZigcode, that.provinceZigcode) && Objects.equals(provinceConfirmCount, that.provinceConfirmCount) && Objects.equals(provinceSuspectedCount, that.provinceSuspectedCount) && Objects.equals(provinceCuredCount, that.provinceCuredCount) && Objects.equals(provinceDeadCount, that.provinceDeadCount) && Objects.equals(updateTime, that.updateTime) && Objects.equals(cityName, that.cityName) && Objects.equals(cityEnglishName, that.cityEnglishName) && Objects.equals(cityZigcode, that.cityZigcode) && Objects.equals(cityConfirmCount, that.cityConfirmCount) && Objects.equals(citySuspectedCount, that.citySuspectedCount) && Objects.equals(cityCuredCount, that.cityCuredCount) && Objects.equals(cityDeadCount, that.cityDeadCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(provinceName, provinceEnglishName, provinceZigcode, provinceConfirmCount, provinceSuspectedCount, provinceCuredCount, provinceDeadCount, updateTime, cityName, cityEnglishName, cityZigcode, cityConfirmCount, citySuspectedCount, cityCuredCount, cityDeadCount);
    }
}
