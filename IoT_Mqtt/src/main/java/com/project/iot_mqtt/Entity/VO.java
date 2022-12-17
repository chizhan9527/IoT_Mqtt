package com.project.iot_mqtt.Entity;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author wlf 1557177832@qq.com
 * @version 2022/12/14 20:59
 * @since JDK18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VO {
    private Integer conformCount;
    private Integer curedCount;
    private Integer deadCount;
    private String time;
}
