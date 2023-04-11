package com.zhaofujun.nest3.impl.ehcache;


import com.zhaofujun.nest3.application.provider.CacheProvider;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import java.util.HashMap;
import java.util.Map;

/**
 * EhCache 缓存提供者的实现
 *
 * @author Jove
 */
public class EhcacheProvider implements CacheProvider {
    public final static String PROVIDER_CODE = "EhcacheProvider";

    @Override
    public String getCode() {
        return PROVIDER_CODE;
    }

    private CacheManager manager = CacheManager.create();

    public String get(String groupName, String key) {
        if (!manager.cacheExists(groupName)) return null;
        Cache cache = manager.getCache(groupName);
        Element el = cache.get(key);
        if (el != null) {
            return el.getObjectValue().toString();
        }
        return null;
    }

    public Map<String, String> get(String groupName, String... keys) {
        Map<String, String> result = new HashMap<String, String>();
        for (String key : keys) {
            String val = get(groupName, key);
            result.put(key, val);
        }
        return result;
    }


    public void put(String groupName, String key, String value, long idleSeconds) {
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
