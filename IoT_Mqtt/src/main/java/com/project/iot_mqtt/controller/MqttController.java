package com.project.iot_mqtt.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.project.iot_mqtt.Entity.*;
import com.project.iot_mqtt.config.MqttConfig;
import com.project.iot_mqtt.mapper.ChinaMapper;
import com.project.iot_mqtt.mapper.NationMapper;
import com.project.iot_mqtt.service.MqttGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;


@RestController
public class MqttController {

    @Autowired
    private NationMapper nationMapper;
    @Autowired
    private ChinaMapper chinaMapper;

    @Resource
    private MqttGateway mqttGateway;

    @Resource
    private MqttConfig mqttConfig;

    @PostMapping("/send_to_china")
    public MyMessage sendTOChina(@RequestBody DTO dto) throws ParseException {
        String time = dto.getTime();
        String cityName = dto.getCityName();
        String provinceName = dto.getProvinceName();
        String countryName = dto.getCountryName();
        Integer length = dto.getLength();

        DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");
        Date date = fmt.parse(time);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH,length);
        Date date1 = calendar.getTime();

        if(cityName!=null) {
            LambdaQueryWrapper<China> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(China::getProvinceName, provinceName);
            queryWrapper.eq(China::getCityName, cityName);
            queryWrapper.between(China::getUpdateTime, date, date1);
            List<China> chains = chinaMapper.selectList(queryWrapper);
            List<VO> voList = copyChinaList(chains);
            return MyMessage.success(voList);
        }
        else{
            LambdaQueryWrapper<China> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(China::getProvinceName, provinceName);
            queryWrapper.between(China::getUpdateTime, date, date1);
            List<China> chains = chinaMapper.selectList(queryWrapper);
            List<VO> voList = copyChinaList2(chains);
            List<VO> voList1 = removeDuplicate(voList);

            return MyMessage.success(voList1);
        }
    }

    private List<VO> removeDuplicate(List<VO> voList) {
        for  ( int  i  =   0 ; i  <  voList.size()  -   1 ; i ++ )  {
            for  ( int  j  =  voList.size()  -   1 ; j  >  i; j -- )  {
                if  (voList.get(j).getTime().equals(voList.get(i).getTime()))  {
                    voList.remove(j);
                }
            }
        }
        return voList;
    }

    private List<VO> copyChinaList2(List<China> chains) {
        List<VO> voList = new ArrayList<>();
        for (China china :
                chains) {
            voList.add(copyChina2(china));
        }
        return voList;
    }

    private List<VO> copyChinaList(List<China> chains) {
        List<VO> voList = new ArrayList<>();
        for (China china :
                chains) {
            voList.add(copyChina(china));
        }
        return voList;
    }

    private VO copyChina2(China china) {
        VO vo = new VO();
        vo.setConformCount(china.getProvinceConfirmCount());
        vo.setCuredCount(china.getProvinceCuredCount());
        vo.setDeadCount(china.getProvinceDeadCount());
        vo.setTime(china.getUpdateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        return vo;
    }

    private VO copyChina(China china) {
        VO vo = new VO();
        vo.setCuredCount(china.getCityCuredCount());
        vo.setDeadCount(china.getCityDeadCount());
        vo.setConformCount(china.getCityConfirmCount());
        vo.setTime(china.getUpdateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        return vo;
    }

    @PostMapping("/send_to_nation")
    public MyMessage send(@RequestBody DTO dto) throws ParseException {
        String time = dto.getTime();
        String cityName = dto.getCityName();
        String provinceName = dto.getProvinceName();
        String countryName = dto.getCountryName();
        Integer length = dto.getLength();


        DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");
        Date date = fmt.parse(time);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH,length);
        Date date1 = calendar.getTime();


        LambdaQueryWrapper<Nation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Nation::getCountryName,countryName);
        queryWrapper.between(Nation::getUpdateTime,date,date1);
        List<Nation> nations = nationMapper.selectList(queryWrapper);

        List<VO> voList = new ArrayList<>();
        voList = this.copyList(nations);

        MyMessage myMessage = MyMessage.success(voList);

        //MyMessage myMessage1 = new MyMessage("/topic","test");
        // 发送消息到指定主题
        mqttGateway.sendToMqtt(myMessage.getTopic(), 1, myMessage.getContent());

        return myMessage;
/*        Nation nation = nationMapper.getNation(10);
        MyMessage myMessage = MyMessage.success(nation);
        return myMessage;*/
        //mqttGateway.sendToMqtt(myMessage.getTopic(), 1, myMessage.getContent());
        //return "send topic: " + myMessage.getTopic()+ ", message : " + myMessage.getContent();
    }

    private List<VO> copyList(List<Nation> nations) {
        List<VO> voList = new ArrayList<>();
        for (Nation nation :
                nations) {
            voList.add(copy(nation));
        }
        return voList;
    }

    private VO copy(Nation nation) {
        VO vo = new VO();
        vo.setConformCount(nation.getConfirmCount());
        vo.setDeadCount(nation.getDeadCount());
        vo.setCuredCount(nation.getCuredCount());
        vo.setTime(nation.getUpdateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        return vo;
    }

    @PutMapping("/topic")
    public String addTopic(@RequestParam("topic")String topic){
        mqttConfig.mqttSubscriber().addTopic(topic,1);
        return "ok";
    }

}

