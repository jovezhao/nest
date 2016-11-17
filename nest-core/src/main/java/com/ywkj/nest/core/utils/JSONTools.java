package com.ywkj.nest.core.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

public class JSONTools {

    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
                objectMapper
                        .configure(SerializationFeature.INDENT_OUTPUT, true)
                .setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        objectMapper.registerModule(new JodaModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);


    }

    public static String toJsonString(Object o){
        try {
            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JsonNode parse(String jsonStr){
        try {
            return  objectMapper.readTree(jsonStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public   static Object toObj(String jsonStr,Class classs){
        try {
            return   objectMapper.readValue(jsonStr, classs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public   static Object toObjList(String jsonStr,Class classs){
        try {
//            MyClass[] myObjects = mapper.readValue(json, MyClass[].class);
//            List<MyClass> myObjects = mapper.readValue(jsonInput, new TypeReference<List<MyClass>>(){});
//            List<MyClass> myObjects = mapper.readValue(jsonInput, mapper.getTypeFactory().constructCollectionType(List.class, MyClass.class));
            return   objectMapper.readValue(jsonStr, objectMapper.getTypeFactory().constructCollectionType(List.class, classs));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
//        String fcuk="{\"data\":[{\"areaCode\":140000,\"areaName\":\"山西\"},{\"areaCode\":150000,\"areaName\":\"内蒙古\"},{\"areaCode\":210000,\"areaName\":\"辽宁\"},{\"areaCode\":320000,\"areaName\":\"江苏\"},{\"areaCode\":330000,\"areaName\":\"浙江\"},{\"areaCode\":340000,\"areaName\":\"安徽\"},{\"areaCode\":350000,\"areaName\":\"福建\"},{\"areaCode\":370000,\"areaName\":\"山东\"},{\"areaCode\":420000,\"areaName\":\"湖北\"},{\"areaCode\":430000,\"areaName\":\"湖南\"},{\"areaCode\":440000,\"areaName\":\"广东\"},{\"areaCode\":500000,\"areaName\":\"重庆\"},{\"areaCode\":510000,\"areaName\":\"四川\"},{\"areaCode\":520000,\"areaName\":\"贵州\"},{\"areaCode\":530000,\"areaName\":\"云南\"},{\"areaCode\":650000,\"areaName\":\"新疆\"},{\"areaCode\":990000,\"areaName\":\"测试省\"}],\"msg\":\"\",\"status\":1}";
//        AreasJsons o = (AreasJsons)JSONTools.toObj(fcuk, AreasJsons.class);
//        System.out.println(o.getData());

    }

}



