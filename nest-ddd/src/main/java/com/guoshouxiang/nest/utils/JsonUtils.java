package com.guoshouxiang.nest.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.stream.Collectors;


public class JsonUtils {
    public static String toJsonString(Object object) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();
        return gson.toJson(object);
    }

    public static <T> T toObj(String jsonString, Class<T> tClass) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();
        return gson.fromJson(jsonString, tClass);
    }

    public static <T> List<T> toListObj(String jsonString, Class<T> tClass) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();
        List list = gson.fromJson(jsonString, List.class);

        List<T> collect = (List<T>) list.stream().map(p -> gson.fromJson(gson.toJson(p), tClass)).collect(Collectors.toList());
        return collect;
    }


}


