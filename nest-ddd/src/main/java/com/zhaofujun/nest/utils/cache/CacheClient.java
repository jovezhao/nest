package com.zhaofujun.nest.utils.cache;

import java.util.Map;

public interface CacheClient {


    String getCode();

    <T> T get(Class<T> tClass, String key);

    String get(String key);

    <T> Map<String, T> get(Class<T> tClass, String... keys);

    void put(String key, Object value, long idleSeconds);

    void put(String key, String value, long idleSeconds);

    void put(String key, Object value);

    void put(String key, String value);

    boolean remove(String key);

    void removeAll();

    boolean containsKey(String key);

    String[] getKeys();

    

}