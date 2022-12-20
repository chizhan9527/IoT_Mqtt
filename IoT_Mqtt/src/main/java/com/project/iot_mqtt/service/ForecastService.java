package com.project.iot_mqtt.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ForecastService {
    // 读取JSON方法
    public Object read(String fileName) {
        String jsonStr = "";

        try {
            File jsonFile = new File(fileName);
            FileReader fileReader = new FileReader(jsonFile);
            Reader reader = new InputStreamReader(new FileInputStream(jsonFile), StandardCharsets.UTF_8);

            int ch = 0;
            StringBuffer stringBuffer = new StringBuffer();
            while (-1 != (ch = reader.read())) {
                stringBuffer.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = stringBuffer.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 调用forecast.py
    public JSONArray forecast(ArrayList<Integer> data, int target,String dataType) {
        JSONObject dataObject = new JSONObject();
        dataObject.put("target", target);
        dataObject.put(dataType, data);
        OutputStreamWriter outputStreamWriter = null;

        try {
            outputStreamWriter = new OutputStreamWriter(
                    new FileOutputStream("IoT_Mqtt/src/main/java/com/project/iot_mqtt/python/data/"+ dataType + ".json"),
                    StandardCharsets.UTF_8);
            outputStreamWriter.write(dataObject.toString());
            outputStreamWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] cmdArr = new String[] {"python3", "IoT_Mqtt/src/main/java/com/project/iot_mqtt/python/forecast.py"};
        Process process = null;

        try {
            process = Runtime.getRuntime().exec(cmdArr);
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        String targetFileName = "conformData";
        if (dataType == "conformData") {
            targetFileName = "conform_forecast";
        } else if ("curedData" == dataType) {
            targetFileName = "cured_forecast";
        } else if ("deadData" == dataType) {
            targetFileName = "dead_forecast";
        } else {
            return null;
        }

        String ansFile = read("IoT_Mqtt/src/main/java/com/project/iot_mqtt/python/data/" + targetFileName +".json").toString();
        JSONObject ansObject = JSONObject.parseObject(ansFile);
        String key = null;
        switch (dataType) {
            case "conformData":
                key = "conformForecast";
                break;
            case "curedData":
                key = "curedForecast";
                break;
            case "deadData":
                key = "deadForecast";
                break;
            default:
                key = null;
                break;
        }
        JSONArray ans = ansObject.getJSONArray(key);
        return ans;
    }
}
