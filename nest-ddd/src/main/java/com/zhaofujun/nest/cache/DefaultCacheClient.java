package com.zhaofujun.nest.cache;

import com.zhaofujun.nest.cache.provider.CacheProvider;
import com.zhaofujun.nest.configuration.CacheConfiguration;
import com.zhaofujun.nest.core.CacheClient;

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


    public <T> T get(Class<T> clazz, String key) {
        return cacheProvider.get(cacheConfiguration.getName(), key, clazz);
    }


    public <T> Map<String, T> get(Class<T> clazz, String... keys) {
        return cacheProvider.get(cacheConfiguration.getName(), clazz, keys);
    }


    public void put(String key, Object value, long idleSeconds) {
        cacheProvider.put(cacheConfiguration.getName(), key, value, idleSeconds);
    }


    public void put(String key, Object value) {
        cacheProvider.put(cacheConfiguration.getName(), key, value, cacheConfiguration.getIdleSeconds());
    }


    public boolean remove(String key) {
        return cacheProvider.remove(cacheConfiguration.getName(), key);
    }


    public void removeAll() {
        cacheProvider.removeAll(cacheConfiguration.getName());
    }


    public boolean containsKey(String key) {
        return cacheProvider.containsKey(cacheConfiguration.getName(), key);
    }

    public String[] getKeys() {
        return cacheProvider.getKeys(cacheConfiguration.getName());
    }
}
