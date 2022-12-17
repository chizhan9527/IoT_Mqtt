package com.project.iot_mqtt.Entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * @author wlf 1557177832@qq.com
 * @version 2022/12/14 19:16
 * @since JDK18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "`foreign`")
public class Nation {
    private Integer id;
    private String continentName;
    private String continentEnglishName;
    private String countryName;
    private String countryEnglishName;
    private String provinceName;
    private String provinceEnglishName;
    private Integer zigCode;
    private Integer confirmCount;
    private Integer suspectedCount;
    private Integer curedCount;
    private Integer deadCount;
    private LocalDateTime updateTime;
}
