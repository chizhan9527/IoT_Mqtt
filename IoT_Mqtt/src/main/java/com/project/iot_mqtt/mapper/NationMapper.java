package com.project.iot_mqtt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.project.iot_mqtt.Entity.DTO;
import com.project.iot_mqtt.Entity.Nation;
import com.project.iot_mqtt.Entity.VO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NationMapper extends BaseMapper<Nation> {

    @Select("select * from `foreign` where id = #{id}")
    Nation getNation(Integer id);
}
