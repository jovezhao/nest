package com.jovezhao.nest.core.cache;

import java.util.Map;

/**
 * 缓存客户端
 *
 * @author Jove
 */
public class CacheClient {

    private CacheGroupStrategy strategy;
    private ICacheProvider provider;

    public CacheClient(String groupName, CacheGroupStrategy strategy) {
        this.strategy = strategy;
        provider = strategy.getProvider();
    }

//    /**
//     * 获取缓存项内容
//     *
//     * @param key
//     * @return
//     */
//    public Object get(String key) {
//        return provider.get(strategy.getName(), key);
//    }

    public <T> T get(Class<T> clazz, String key) {
        return provider.get(strategy.getName(), key,clazz);
    }

    /**
     * 获取一组缓存项的Map列表
     *
     * @param keys
     * @return
     */
    public <T> Map<String, T> get(Class<T> clazz, String... keys) {
        return provider.get(strategy.getName(),clazz, keys);
    }

    /**
     * 按过期时间增加或修改缓存项
     *
     * @param key
     * @param value
     * @param idleSeconds
     */
    public void put(String key, Object value, long idleSeconds) {
        provider.put(strategy.getName(), key, value, idleSeconds);
    }

    /**
     * 按默认配置的组策略增加或修改缓存项
     *
     * @param key
     * @param value
     */
    public void put(String key, Object value) {
        provider.put(strategy.getName(), key, value, strategy.getIdleSeconds());
    }

    /**
     * 移出组策略下的指定缓存项
     *
     * @param key
     * @return
     */
    public boolean remove(String key) {
        return provider.remove(strategy.getName(), key);
    }

    /**
     * 移出组策略下的所有缓存项
     */
    public void removeAll() {
        provider.removeAll(strategy.getName());
    }

    /**
     * 检查是否存在当前缓存键
     *
     * @param key
     * @return
     */
    public boolean containsKey(String key) {
        return provider.containsKey(strategy.getName(), key);
    }

}
