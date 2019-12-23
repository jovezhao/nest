package com.zhaofujun.nest.json;

public class JsonConfiguration {
    private Class clazz;

    public JsonConfiguration(Class clazz) {
        this.clazz = clazz;
    }

    public Class getClazz() {
        return clazz;
    }
}
