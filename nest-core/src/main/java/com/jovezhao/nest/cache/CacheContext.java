package com.jovezhao.nest.cache;

import java.util.Map;

/**
 * 缓存上下文
 *
 * @author Jove
 */
public class CacheContext {

    private CacheItem cacheItem;
    private ICacheProvider provider;

    private CacheContext(CacheItem cacheItem) {
        this.cacheItem = cacheItem;
        provider = cacheItem.getProvider();
    }

    /**
     * 通过缓存代号来获取一个缓存上下文
     *
     * @param cacheCode
     * @return
     */
    public static CacheContext getContextByCode(String cacheCode) {
        CacheItem cacheItem = CacheItemManager.get(cacheCode);
        return new CacheContext(cacheItem);
    }
//

    public <T> T get(Class<T> clazz, String key) {
        return provider.get(cacheItem.getName(), key, clazz);
    }

    /**
     * 获取一组缓存项的Map列表
     *
     * @param keys
     * @return
     */
    public <T> Map<String, T> get(Class<T> clazz, String... keys) {
        return provider.get(cacheItem.getName(), clazz, keys);
    }

    /**
     * 按过期时间增加或修改缓存项
     *
     * @param key
     * @param value
     * @param idleSeconds
     */
    public void put(String key, Object value, long idleSeconds) {
        provider.put(cacheItem.getName(), key, value, idleSeconds);
    }

    /**
     * 按默认配置的组策略增加或修改缓存项
     *
     * @param key
     * @param value
     */
    public void put(String key, Object value) {
        provider.put(cacheItem.getName(), key, value, cacheItem.getIdleSeconds());
    }

    /**
     * 移出组策略下的指定缓存项
     *
     * @param key
     * @return
     */
    public boolean remove(String key) {
        return provider.remove(cacheItem.getName(), key);
    }

    /**
     * 移出组策略下的所有缓存项
     */
    public void removeAll() {
        provider.removeAll(cacheItem.getName());
    }

    /**
     * 检查是否存在当前缓存键
     *
     * @param key
     * @return
     */
    public boolean containsKey(String key) {
        return provider.containsKey(cacheItem.getName(), key);
    }

    public String[] getKeys() {
        return provider.getKeys(cacheItem.getName());
    }
}
