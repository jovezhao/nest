package com.zhaofujun.nest.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhaofujun.nest.core.BeanFinder;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class JsonCreator {
    private Gson gson;
    private BeanFinder beanFinder;

    public JsonCreator(BeanFinder beanFinder) {

        String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
        String dateFormat = "yyyy-MM-dd";

        this.beanFinder = beanFinder;
        GsonBuilder gsonBuilder = new GsonBuilder()
                .disableHtmlEscaping()
                .setDateFormat(dateTimeFormat)
                .registerTypeAdapterFactory(new EntityTypeAdapterFactory())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter(dateTimeFormat))
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter(dateFormat))
                .serializeNulls();

        Set<JsonConfiguration> jsonConfigurations = beanFinder.getInstances(JsonConfiguration.class);
        for (JsonConfiguration p : jsonConfigurations) {
            gsonBuilder = gsonBuilder.registerTypeAdapter(p.getClazz(), p.getTypeAdapter());
        }
        gson = gsonBuilder.create();
    }


    public String toJsonString(Object object) {
        return gson.toJson(object);
    }

    public <T> T toObj(String jsonString, Class<T> tClass) {
        return gson.fromJson(jsonString, tClass);
    }

    public <T> List<T> toListObj(String jsonString, Class<T> tClass) {

        List list = gson.fromJson(jsonString, List.class);
        List<T> collect = (List<T>) list.stream().map(p -> gson.fromJson(gson.toJson(p), tClass)).collect(Collectors.toList());
        return collect;
    }

    public <T> T toObj(String jsonString, Type typeOfT) {
        return gson.fromJson(jsonString, typeOfT);
    }

}


