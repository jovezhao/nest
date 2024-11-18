package com.zhaofujun.nest.provider;


import java.util.Map;

/**
 * 缓存策略
 *
 * @author Jove
 */
public interface CacheProvider extends Provider {

     String get(String groupName, String key);


     Map<String, String> get(String groupName, String... keys);


    void put(String groupName, String key, String value, long idleSeconds);


    boolean remove(String groupName, String key);


    void removeAll(String groupName);


    boolean containsKey(String groupName, String key);


    String[] getKeys(String groupName);
}
