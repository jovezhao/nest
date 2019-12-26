package com.zhaofujun.nest.json;

import java.util.Objects;

public class JsonConfiguration {
    private Class clazz;
    private Object typeAdapter;

    public JsonConfiguration(Class clazz) {
        this.clazz = clazz;
    }

    public JsonConfiguration(Class clazz, Object typeAdapter) {
        this.clazz = clazz;
        this.typeAdapter = typeAdapter;
    }

    public Class getClazz() {
        return clazz;
    }

    public Object getTypeAdapter() {
        return typeAdapter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JsonConfiguration that = (JsonConfiguration) o;
        return Objects.equals(clazz, that.clazz);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clazz);
    }
}
