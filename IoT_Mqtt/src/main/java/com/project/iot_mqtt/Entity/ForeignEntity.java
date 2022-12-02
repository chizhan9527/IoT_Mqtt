package com.project.iot_mqtt.Entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "foreign", schema = "iot_mqtt", catalog = "")
@IdClass(ForeignEntityPK.class)
public class ForeignEntity {
    @Basic
    @Column(name = "continent_name")
    private String continentName;
    @Basic
    @Column(name = "continent_english_name")
    private String continentEnglishName;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "country_name")
    private String countryName;
    @Basic
    @Column(name = "country_english_name")
    private String countryEnglishName;
    @Basic
    @Column(name = "province_name")
    private String provinceName;
    @Basic
    @Column(name = "province_english_name")
    private String provinceEnglishName;
    @Basic
    @Column(name = "zigcode")
    private Integer zigcode;
    @Basic
    @Column(name = "confirm_count")
    private Integer confirmCount;
    @Basic
    @Column(name = "suspected_count")
    private Integer suspectedCount;
    @Basic
    @Column(name = "cured_count")
    private Integer curedCount;
    @Basic
    @Column(name = "dead_count")
    private Integer deadCount;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "update_time")
    private Timestamp updateTime;

    public String getContinentName() {
        return continentName;
    }

    public void setContinentName(String continentName) {
        this.continentName = continentName;
    }

    public String getContinentEnglishName() {
        return continentEnglishName;
    }

    public void setContinentEnglishName(String continentEnglishName) {
        this.continentEnglishName = continentEnglishName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryEnglishName() {
        return countryEnglishName;
    }

    public void setCountryEnglishName(String countryEnglishName) {
        this.countryEnglishName = countryEnglishName;
    }

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

    public Integer getZigcode() {
        return zigcode;
    }

    public void setZigcode(Integer zigcode) {
        this.zigcode = zigcode;
    }

    public Integer getConfirmCount() {
        return confirmCount;
    }

    public void setConfirmCount(Integer confirmCount) {
        this.confirmCount = confirmCount;
    }

    public Integer getSuspectedCount() {
        return suspectedCount;
    }

    public void setSuspectedCount(Integer suspectedCount) {
        this.suspectedCount = suspectedCount;
    }

    public Integer getCuredCount() {
        return curedCount;
    }

    public void setCuredCount(Integer curedCount) {
        this.curedCount = curedCount;
    }

    public Integer getDeadCount() {
        return deadCount;
    }

    public void setDeadCount(Integer deadCount) {
        this.deadCount = deadCount;
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
        ForeignEntity that = (ForeignEntity) o;
        return Objects.equals(continentName, that.continentName) && Objects.equals(continentEnglishName, that.continentEnglishName) && Objects.equals(countryName, that.countryName) && Objects.equals(countryEnglishName, that.countryEnglishName) && Objects.equals(provinceName, that.provinceName) && Objects.equals(provinceEnglishName, that.provinceEnglishName) && Objects.equals(zigcode, that.zigcode) && Objects.equals(confirmCount, that.confirmCount) && Objects.equals(suspectedCount, that.suspectedCount) && Objects.equals(curedCount, that.curedCount) && Objects.equals(deadCount, that.deadCount) && Objects.equals(updateTime, that.updateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(continentName, continentEnglishName, countryName, countryEnglishName, provinceName, provinceEnglishName, zigcode, confirmCount, suspectedCount, curedCount, deadCount, updateTime);
    }
}
