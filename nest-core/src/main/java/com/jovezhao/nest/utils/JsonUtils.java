package com.jovezhao.nest.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;

/**
 * Created by zhaofujun on 2017/3/3.
 */
public class JsonUtils {
    public static String toJsonString(Object object) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().setDateFormat("yyyy-MM-dd").serializeNulls().create();
        return gson.toJson(object);
    }

    public static <T> T toObj(String jsonString, Class<T> tClass) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().setDateFormat("yyyy-MM-dd").serializeNulls().create();
        return gson.fromJson(jsonString, tClass);
    }
}
