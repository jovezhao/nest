package com.zhaofujun.nest.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhaofujun.nest.json.adapter.DateAdapter;
import com.zhaofujun.nest.json.adapter.DomainObjectTypeAdapterFactory;
import com.zhaofujun.nest.json.adapter.LocalDateAdapter;
import com.zhaofujun.nest.json.adapter.LocalDateTimeAdapter;
import com.zhaofujun.nest.standard.DomainObject;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


public class JsonCreator {

    private Gson gson;

    private static JsonCreator jsonCreator = new JsonCreator();

    public static JsonCreator getInstance() {
        return jsonCreator;
    }

    private JsonCreator() {

        GsonBuilder gsonBuilder = new GsonBuilder()
                .disableHtmlEscaping()
                .registerTypeAdapterFactory(new DomainObjectTypeAdapterFactory())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .registerTypeAdapter(Date.class, new DateAdapter())
                .serializeNulls();

        gson = gsonBuilder.create();
    }


    public String toJsonString(Object object) {
        if (DomainObject.class.isInstance(object)) {
            DomainObjectSerializeContext.setCurrent((DomainObject) object);
            DomainObjectSerializeContext.clear();
        }
        String s = gson.toJson(object);
        DomainObjectSerializeContext.clear();
        DomainObjectSerializeContext.clearDomainObject();
        return s;
    }

    public <T> T toObj(String jsonString, Class<T> tClass) {
        return toObj(jsonString, tClass, false);
    }

    public <T> T toObj(String jsonString, Class<T> tClass, boolean intoContext) {
        DomainObjectSerializeContext.setIntoContext(intoContext);
        return gson.fromJson(jsonString, tClass);
    }

    public <T> List<T> toListObj(String jsonString, Class<T> tClass) {

        List list = gson.fromJson(jsonString, List.class);
        List<T> collect = (List<T>) list.stream().map(p -> gson.fromJson(gson.toJson(p), tClass)).collect(Collectors.toList());
        return collect;
    }

    public <T> T toObj(String jsonString, Type typeOfT) {
        return toObj(jsonString, typeOfT, false);
    }

    public <T> T toObj(String jsonString, Type typeOfT, boolean intoContext) {
        DomainObjectSerializeContext.setIntoContext(intoContext);
        return gson.fromJson(jsonString, typeOfT);
    }


}

