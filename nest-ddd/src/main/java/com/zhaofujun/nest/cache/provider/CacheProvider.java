package com.zhaofujun.nest.cache.provider;

import java.util.Map;

/**
 * 缓存策略
 *
 * @author Jove
 */
public interface CacheProvider {

    String getCode();


    <T> T get(String groupName, String key, Class<T> clazz);


    <T> Map<String, T> get(String groupName, Class<T> clazz, String... keys);


    void put(String groupName, String key, Object value, long idleSeconds);


    boolean remove(String groupName, String key);


    void removeAll(String groupName);


    boolean containsKey(String groupName, String key);


    String[] getKeys(String groupName);
}
