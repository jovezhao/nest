package com.zhaofujun.nest3.impl.fastjson;

import com.alibaba.fastjson2.JSON;
import com.zhaofujun.nest3.application.provider.JsonProvider;

import java.lang.reflect.Type;

public class FastjsonProvider implements JsonProvider<Object> {
    @Override
    public String toJsonString(Object object) {
        return JSON.toJSONString(object);
    }

    @Override
    public  Object parseObject(String text, Type type) {
        return JSON.parseObject(text, type);
    }

    @Override
    public String getCode() {
        return "FastjsonProvider";
    }
}
