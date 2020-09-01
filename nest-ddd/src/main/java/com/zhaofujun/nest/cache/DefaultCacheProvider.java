package com.zhaofujun.nest.cache;


import com.zhaofujun.nest.json.JsonCreator;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * EhCache 缓存提供者的实现
 *
 * @author Jove
 */
public class DefaultCacheProvider implements CacheProvider {
    public final static String PROVIDER_CODE = "DEFAULT_CACHE_PROVIDER";

    private JsonCreator jsonCreator= JsonCreator.getInstance();


    @Override
    public String getCode() {
        return PROVIDER_CODE;
    }

    private CacheManager manager = CacheManager.create();

    public Object get(String groupName, String key, Type type) {
        if (!manager.cacheExists(groupName)) return null;
        Cache cache = manager.getCache(groupName);
        Element el = cache.get(key);
        if (el != null) {
            String json = el.getObjectValue().toString();
            return jsonCreator.toObj(json, type,true);
        }
        return null;
    }

    public Map<String, Object> get(String groupName, Type type, String... keys) {
        Map<String, Object> result = new HashMap<String, Object>();
        for (String key : keys) {
            Object val = get(groupName, key, type);
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
            String json=jsonCreator.toJsonString(value);
            Element el = new Element(key, json, false, (int) idleSeconds, 0);
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
