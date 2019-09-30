package com.zhaofujun.nest.cache;

import com.zhaofujun.nest.configuration.CacheConfiguration;
import com.zhaofujun.nest.cache.provider.CacheProvider;

import java.util.Map;

/**
 * 缓存上下文
 *
 * @author Jove
 */
public class CacheClient {

    private CacheProvider cacheProvider;

    private CacheConfiguration cacheConfiguration;


    public  CacheClient(CacheProvider cacheProvider, CacheConfiguration cacheConfiguration) {
        this.cacheProvider = cacheProvider;
        this.cacheConfiguration = cacheConfiguration;
    }


    public <T> T get(Class<T> clazz, String key) {
        return cacheProvider.get(cacheConfiguration.getName(), key, clazz);
    }

    /**
     * 获取一组缓存项的Map列表
     *
     * @param keys
     * @return
     */
    public <T> Map<String, T> get(Class<T> clazz, String... keys) {
        return cacheProvider.get(cacheConfiguration.getName(), clazz, keys);
    }

    /**
     * 按过期时间增加或修改缓存项
     *
     * @param key
     * @param value
     * @param idleSeconds
     */
    public void put(String key, Object value, long idleSeconds) {
        cacheProvider.put(cacheConfiguration.getName(), key, value, idleSeconds);
    }

    /**
     * 按默认配置的组策略增加或修改缓存项
     *
     * @param key
     * @param value
     */
    public void put(String key, Object value) {
        cacheProvider.put(cacheConfiguration.getName(), key, value, cacheConfiguration.getIdleSeconds());
    }

    /**
     * 移出组策略下的指定缓存项
     *
     * @param key
     * @return
     */
    public boolean remove(String key) {
        return cacheProvider.remove(cacheConfiguration.getName(), key);
    }

    /**
     * 移出组策略下的所有缓存项
     */
    public void removeAll() {
        cacheProvider.removeAll(cacheConfiguration.getName());
    }

    /**
     * 检查是否存在当前缓存键
     *
     * @param key
     * @return
     */
    public boolean containsKey(String key) {
        return cacheProvider.containsKey(cacheConfiguration.getName(), key);
    }

    public String[] getKeys() {
        return cacheProvider.getKeys(cacheConfiguration.getName());
    }
}
