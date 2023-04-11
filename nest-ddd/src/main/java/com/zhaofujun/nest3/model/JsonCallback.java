package com.zhaofujun.nest3.model;

@FunctionalInterface
public interface JsonCallback<T extends Entity> {
    void callback(T entity,String jsonString);
}
