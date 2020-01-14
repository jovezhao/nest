package com.zhaofujun.nest.core;

import java.util.Map;

public interface CacheClient {
    <T> T get(Class clazz, String key);


    <T> Map<String, T> get(Class<T> clazz, String... keys);


    void put(String key, Object value, long idleSeconds);

    void put(String key, Object value);

    boolean remove(String key);

    void removeAll();

    boolean containsKey(String key);

    String[] getKeys();
}