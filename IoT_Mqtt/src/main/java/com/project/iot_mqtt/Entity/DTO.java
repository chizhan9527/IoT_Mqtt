package com.project.iot_mqtt.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author wlf 1557177832@qq.com
 * @version 2022/12/14 20:57
 * @since JDK18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DTO {
    private String countryName;
    private String provinceName;
    private String cityName;
    private String time;
    private Integer length;
}
