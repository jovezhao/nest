package com.zhaofujun.nest.cache;

import com.zhaofujun.nest.cache.provider.CacheProvider;
import com.zhaofujun.nest.configuration.CacheConfiguration;
import com.zhaofujun.nest.core.CacheClient;

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


    public  DefaultCacheClient(CacheProvider cacheProvider, CacheConfiguration cacheConfiguration) {
        this.cacheProvider = cacheProvider;
        this.cacheConfiguration = cacheConfiguration;
    }


    public <T> T get(Type type, String key) {
        return cacheProvider.get(cacheConfiguration.getCacheCode(), key, type);
    }


    public <T> Map<String, T> get(Type type, String... keys) {
        return cacheProvider.get(cacheConfiguration.getCacheCode(), type, keys);
    }


    public void put(String key, Object value, long idleSeconds) {
        cacheProvider.put(cacheConfiguration.getCacheCode(), key, value, idleSeconds);
    }


    public void put(String key, Object value) {
        cacheProvider.put(cacheConfiguration.getCacheCode(), key, value, cacheConfiguration.getIdleSeconds());
    }


    public boolean remove(String key) {
        return cacheProvider.remove(cacheConfiguration.getCacheCode(), key);
    }


    public void removeAll() {
        cacheProvider.removeAll(cacheConfiguration.getCacheCode());
    }


    public boolean containsKey(String key) {
        return cacheProvider.containsKey(cacheConfiguration.getCacheCode(), key);
    }

    public String[] getKeys() {
        return cacheProvider.getKeys(cacheConfiguration.getCacheCode());
    }
}
