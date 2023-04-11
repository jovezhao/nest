package com.zhaofujun.nest3.application;

import com.zhaofujun.nest3.application.provider.CacheProvider;
import com.zhaofujun.nest3.application.provider.JsonProvider;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * 缓存上下文
 *
 * @author Jove
 */
public class DefaultCacheClient implements CacheClient {

    private CacheProvider cacheProvider;

    private String code;

    private long idleSeconds;

    private JsonProvider jsonProvider;


    public DefaultCacheClient( String code,CacheProvider cacheProvider, long idleSeconds, JsonProvider jsonProvider) {
        this.cacheProvider = cacheProvider;
        this.code = code;
        this.idleSeconds = idleSeconds;
        this.jsonProvider = jsonProvider;
    }

    public <T> T get(Type type, String key) {
        String json = cacheProvider.get(code, key);
        return (T) jsonProvider.parseObject(json, type);
    }

    @Override
    public String get(String key) {
        return cacheProvider.get(code, key);
    }


    public <T> Map<String, T> get(Type type, String... keys) {
        Map<String, T> tMap = new HashMap<>();
        cacheProvider.get(code, keys).forEach((p, q) -> {
            T t = (T) jsonProvider.parseObject(q, type);
            tMap.put(p, t);
        });
        return tMap;
    }

    @Override
    public Map<String, String> get(String... keys) {
        return cacheProvider.get(code, keys);
    }


    public void put(String key, Object value, long idleSeconds) {
        String jsonString = jsonProvider.toJsonString(value);
        cacheProvider.put(code, key, jsonString, idleSeconds);
    }

    @Override
    public void put(String key, String value, long idleSeconds) {
        cacheProvider.put(code, key, value, idleSeconds);
    }


    public void put(String key, Object value) {
        String jsonString = jsonProvider.toJsonString(value);
        cacheProvider.put(code, key, jsonString, idleSeconds);
    }

    @Override
    public void put(String key, String value) {
        cacheProvider.put(code, key, value,idleSeconds);
    }


    public boolean remove(String key) {
        return cacheProvider.remove(code, key);
    }


    public void removeAll() {
        cacheProvider.removeAll(code);
    }


    public boolean containsKey(String key) {
        return cacheProvider.containsKey(code, key);
    }

    public String[] getKeys() {
        return cacheProvider.getKeys(code);
    }
}
