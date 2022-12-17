package com.project.iot_mqtt.Entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author wlf 1557177832@qq.com
 * @version 2022/12/14 20:52
 * @since JDK18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "china")
public class China {
    private Integer id;
    private String provinceName;
    private String provinceEnglishName;
    private Integer provinceZigCode;
    private Integer provinceConfirmCount;
    private Integer provinceSuspectedCount;
    private Integer provinceCuredCount;
    private Integer provinceDeadCount;
    private LocalDateTime updateTime;
    private String cityName;
    private String cityEnglishName;
    private Integer cityZigCode;
    private Integer cityConfirmCount;
    private Integer citySuspectedCount;
    private Integer cityCuredCount;
    private Integer cityDeadCount;
}
