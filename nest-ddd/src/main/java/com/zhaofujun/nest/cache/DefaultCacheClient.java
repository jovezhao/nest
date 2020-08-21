package com.zhaofujun.nest.cache;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * 缓存上下文
 *
 * @author Jove
 */
class DefaultCacheClient implements CacheClient {

    private CacheProvider cacheProvider;

    private CacheConfiguration cacheConfiguration;


    public DefaultCacheClient(CacheProvider cacheProvider, CacheConfiguration cacheConfiguration) {
        this.cacheProvider = cacheProvider;
        this.cacheConfiguration = cacheConfiguration;
    }


    public <T> T get(Type type, String key) {
        return cacheProvider.get(cacheConfiguration.getCode(), key, type);
    }


    public <T> Map<String, T> get(Type type, String... keys) {
        return cacheProvider.get(cacheConfiguration.getCode(), type, keys);
    }


    public void put(String key, Object value, long idleSeconds) {
        cacheProvider.put(cacheConfiguration.getCode(), key, value, idleSeconds);
    }


    public void put(String key, Object value) {
        cacheProvider.put(cacheConfiguration.getCode(), key, value, cacheConfiguration.getIdleSeconds());
    }


    public boolean remove(String key) {
        return cacheProvider.remove(cacheConfiguration.getCode(), key);
    }


    public void removeAll() {
        cacheProvider.removeAll(cacheConfiguration.getCode());
    }


    public boolean containsKey(String key) {
        return cacheProvider.containsKey(cacheConfiguration.getCode(), key);
    }

    public String[] getKeys() {
        return cacheProvider.getKeys(cacheConfiguration.getCode());
    }
}
