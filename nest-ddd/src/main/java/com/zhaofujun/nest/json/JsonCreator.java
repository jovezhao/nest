package com.zhaofujun.nest.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhaofujun.nest.core.BeanFinder;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class JsonCreator {
    private Gson gson;
    private BeanFinder beanFinder;

    public JsonCreator(BeanFinder beanFinder) {

        EntityObjectAdapter objectAdapter = new EntityObjectAdapter();
        this.beanFinder = beanFinder;
        GsonBuilder gsonBuilder = new GsonBuilder()
                .disableHtmlEscaping()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .serializeNulls();
        Set<JsonConfiguration> jsonConfigurations = beanFinder.getInstances(JsonConfiguration.class);
        for (JsonConfiguration p : jsonConfigurations) {
            gsonBuilder = gsonBuilder.registerTypeAdapter(p.getClazz(), objectAdapter);
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


}


