package com.project.iot_mqtt.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.project.iot_mqtt.Entity.*;
import com.project.iot_mqtt.config.MqttConfig;
import com.project.iot_mqtt.mapper.ChinaMapper;
import com.project.iot_mqtt.mapper.NationMapper;
import com.project.iot_mqtt.service.ForecastService;
import com.project.iot_mqtt.service.MqttGateway;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
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

        if (length != 0) {
            DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            Date date = fmt.parse(time);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, length - 1);
            Date date1 = calendar.getTime();

            LambdaQueryWrapper<China> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(China::getProvinceName, provinceName);
            if (cityName != null) {
                queryWrapper.eq(China::getCityName, cityName);
                queryWrapper.between(China::getUpdateTime, date, date1);
                List<China> chains = chinaMapper.selectList(queryWrapper);
                List<VO> voList = copyChinaList(chains);
                return MyMessage.success(voList);
            } else {
                queryWrapper.between(China::getUpdateTime, date, date1);
                List<China> chains = chinaMapper.selectList(queryWrapper);
                List<VO> voList = copyChinaList2(chains);
                List<VO> voList1 = removeDuplicate(voList);
                return MyMessage.success(voList1);
            }
        } else {
            String time1 = "2021-01-01";
            if (Objects.equals(time, "2020")) {
                time = "2020-01-01";
            } else if (Objects.equals(time, "2021")) {
                time = "2021-01-01";
                time1 = "2022-01-01";
            }
            DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            Date date = fmt.parse(time);
            Date test = fmt.parse(time1);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.MONTH, 1);
            Date date1 = calendar.getTime();

            List<China> chinaList = new ArrayList<>();

            if (cityName != null) {
                while (true) {
                    LambdaQueryWrapper<China> queryWrapper = new LambdaQueryWrapper<>();
                    queryWrapper.eq(China::getProvinceName, provinceName);
                    queryWrapper.eq(China::getCityName, cityName);
                    queryWrapper.between(China::getUpdateTime, date, date1);
                    queryWrapper.last("limit 1");
                    China china = chinaMapper.selectOne(queryWrapper);
                    if (china != null) {
                        chinaList.add(china);
                    }
                    calendar.setTime(date);
                    calendar.add(Calendar.MONTH, 1);
                    date = calendar.getTime();
                    calendar.setTime(date1);
                    calendar.add(Calendar.MONTH, 1);
                    date1 = calendar.getTime();
                    if (date.equals(test))
                        break;
                }
                List<VO> voList = copyChinaList(chinaList);
                return MyMessage.success(voList);
            } else {
                while (true) {
                    LambdaQueryWrapper<China> queryWrapper = new LambdaQueryWrapper<>();
                    queryWrapper.eq(China::getProvinceName, provinceName);
                    queryWrapper.between(China::getUpdateTime, date, date1);
                    queryWrapper.last("limit 1");
                    China china = chinaMapper.selectOne(queryWrapper);
                    if (china != null) {
                        chinaList.add(china);
                    }
                    calendar.setTime(date);
                    calendar.add(Calendar.MONTH, 1);
                    date = calendar.getTime();
                    calendar.setTime(date1);
                    calendar.add(Calendar.MONTH, 1);
                    date1 = calendar.getTime();
                    if (date.equals(test))
                        break;
                }
                List<VO> voList = copyChinaList2(chinaList);
                List<VO> voList1 = removeDuplicate(voList);
                return MyMessage.success(voList1);
            }
        }


    }

    private List<VO> removeDuplicate(List<VO> voList) {
        for (int i = 0; i < voList.size() - 1; i++) {
            for (int j = voList.size() - 1; j > i; j--) {
                if (voList.get(j).getTime().equals(voList.get(i).getTime())) {
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

        if (length != 0) {
            DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            Date date = fmt.parse(time);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, length - 1);
            Date date1 = calendar.getTime();


            LambdaQueryWrapper<Nation> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Nation::getCountryName, countryName);
            queryWrapper.between(Nation::getUpdateTime, date, date1);
            List<Nation> nations = nationMapper.selectList(queryWrapper);

            List<VO> voList = new ArrayList<>();
            voList = this.copyList(nations);

            MyMessage myMessage = MyMessage.success(voList);

            //MyMessage myMessage1 = new MyMessage("/topic","test");
            // 发送消息到指定主题
            //qttGateway.sendToMqtt(myMessage.getTopic(), 1, myMessage.getContent());

            return myMessage;
        } else {
            String time1 = "2021-01-01";
            if (Objects.equals(time, "2020")) {
                time = "2020-01-01";
            } else if (Objects.equals(time, "2021")) {
                time = "2021-01-01";
                time1 = "2022-01-01";
            }
            DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            Date date = fmt.parse(time);
            Date test = fmt.parse(time1);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.MONTH, 1);
            Date date1 = calendar.getTime();

            List<Nation> nations = new ArrayList<>();

            while (true) {
                LambdaQueryWrapper<Nation> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(Nation::getCountryName, countryName);
                queryWrapper.between(Nation::getUpdateTime, date, date1);
                queryWrapper.last("limit 1");
                Nation nation = nationMapper.selectOne(queryWrapper);
                if (nation != null) {
                    nations.add(nation);
                }
                calendar.setTime(date);
                calendar.add(Calendar.MONTH, 1);
                date = calendar.getTime();
                calendar.setTime(date1);
                calendar.add(Calendar.MONTH, 1);
                date1 = calendar.getTime();
                if (date.equals(test))
                    break;
            }
            List<VO> voList = copyList(nations);
            return MyMessage.success(voList);

        }
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
    public String addTopic(@RequestParam("topic") String topic) {
        mqttConfig.mqttSubscriber().addTopic(topic, 1);
        return "ok";
    }


    //---------------------------------------- forecast---------------------------------------- //


    @PostMapping("/forecast/getCountryForecast")
    public MyMessage getCountryForecast(@RequestBody DTO dto) throws ParseException {
        ForecastService forecastService = new ForecastService();
        dto.setLength(0);
        dto.setProvinceName(null);
        dto.setCityName(null);
        // 这里可能需要改一下，前端需求先假设是按照2021年预测2022年的每个月。
        if (Objects.equals(dto.getTime(), "2022")) {
            dto.setTime("2021");
        }
        String countryName = dto.getCountryName();

        MyMessage message = this.send(dto);
        Object data = message.getContent();
        List<Map<String, Integer>> resultList = new ArrayList<>();
        if (null != data) {
            // 转换类型，否则不好使用List相关方法
            List<VO> data1 = (List<VO>) data;
            // 12个月对应的确诊、治愈、死亡数字
            ArrayList<Integer> conformCounts = new ArrayList<>();
            ArrayList<Integer> curedCounts = new ArrayList<>();
            ArrayList<Integer> deadCounts = new ArrayList<>();
            // 从将send中获取的数据添加在对应的List中
            for (VO vo : data1) {
                conformCounts.add(vo.getConformCount());
                curedCounts.add(vo.getCuredCount());
                deadCounts.add(vo.getDeadCount());
            }

            JSONArray conformForeJsonArray = forecastService.forecast(conformCounts, 12, "conformData");
            JSONArray curedForeJsonArray = forecastService.forecast(curedCounts, 12, "curedData");
            JSONArray deadForeJsonArray = forecastService.forecast(deadCounts, 12, "deadData");

            List<BigDecimal> conformForeList = JSONObject.parseArray(conformForeJsonArray.toJSONString());
            List<BigDecimal> curedeForeList = JSONObject.parseArray(curedForeJsonArray.toJSONString());
            List<BigDecimal> deadForeList = JSONObject.parseArray(deadForeJsonArray.toJSONString());


            for (int i = 0; i < conformForeList.size(); i++) {
                Map<String, Integer> map = new HashMap<>();
                map.put("deadForecast", deadForeList.get(i).intValue());
                map.put("cureForecast", curedeForeList.get(i).intValue());
                map.put("conformForecast", conformForeList.get(i).intValue());
                resultList.add(map);
            }
        } else {
            return null;
        }
        addTopic("forecast/");
        return MyMessage.success("forecast/", resultList);
    }
}

