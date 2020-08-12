//package com.zhaofujun.nest.utils;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.zhaofujun.nest.json.EntityTypeAdapterFactory;
//
//import java.lang.reflect.Type;
//import java.util.List;
//import java.util.stream.Collectors;
//
///**
// *
// **/
//public class JsonUtils {
//
//    private static Gson gson=  (new GsonBuilder()).disableHtmlEscaping().setDateFormat("yyyy-MM-dd HH:mm:ss").registerTypeAdapterFactory(new EntityTypeAdapterFactory()).serializeNulls().create();;
//
//    public JsonUtils() {
//    }
//
//
//    public static String toJsonString(Object object) {
//        return gson.toJson(object);
//    }
//
//    public static <T> T toObj(String jsonString, Class<T> tClass) {
//        return gson.fromJson(jsonString, tClass);
//    }
//
//    public static <T> List<T> toListObj(String jsonString, Class<T> tClass) {
//
//        List list = gson.fromJson(jsonString, List.class);
//        List<T> collect = (List<T>) list.stream().map(p -> gson.fromJson(gson.toJson(p), tClass)).collect(Collectors.toList());
//        return collect;
//    }
//
//    public static <T> T toObj(String jsonString, Type typeOfT) {
//        return gson.fromJson(jsonString, typeOfT);
//    }
//}
