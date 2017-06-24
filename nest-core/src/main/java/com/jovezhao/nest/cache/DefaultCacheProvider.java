package com.jovezhao.nest.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.ObjectExistsException;

import java.util.HashMap;
import java.util.Map;

/**
 * EhCache 缓存提供者的实现
 *
 * @author Jove
 */
class DefaultCacheProvider implements ICacheProvider {


    private CacheManager manager = CacheManager.create();

    public Object get(String groupName, String key, Class clazz) {
        if (!manager.cacheExists(groupName)) return null;
        Cache cache = manager.getCache(groupName);
        Element el = cache.get(key);
        if (el != null)
            return el.getObjectValue();
        return null;
    }

    public Map<String, Object> get(String groupName, Class clazz, String... keys) {
        Map<String, Object> result = new HashMap<String, Object>();
        for (String key : keys) {
            Object val = get(groupName, key, clazz);
            result.put(key, val);
        }
        return result;
    }

    public void put(String groupName, String key, Object value, long idleSeconds) {
        if (!manager.cacheExists(groupName)) {
            manager.addCache(groupName);
        }
        Cache cache = manager.getCache(groupName);
        synchronized (value) {
            Element el = new Element(key, value, false, (int) idleSeconds, 0);
            cache.put(el);
        }

    }

    public boolean remove(String groupName, String key) {
        if (!manager.cacheExists(groupName))
            return true;
        Cache cache = manager.getCache(groupName);
        return cache.remove(key);
    }

    public void removeAll(String groupName) {
        if (!manager.cacheExists(groupName))
            return;
        Cache cache = manager.getCache(groupName);
        cache.removeAll();
    }

    public boolean containsKey(String groupName, String key) {
        if (!manager.cacheExists(groupName))
            return false;
        Cache cache = manager.getCache(groupName);
        return cache.isKeyInCache(key) && cache.get(key) != null;
    }

    @Override
    public String[] getKeys(String groupName) {
        if (!manager.cacheExists(groupName)) return null;
        Cache cache = manager.getCache(groupName);

        return (String[]) cache.getKeys().toArray();
    }


}
