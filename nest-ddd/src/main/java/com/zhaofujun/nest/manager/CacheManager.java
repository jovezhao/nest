package com.zhaofujun.nest.manager;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.zhaofujun.nest.NestConst;
import com.zhaofujun.nest.utils.JsonUtil;
import com.zhaofujun.nest.utils.cache.CacheClient;
import com.zhaofujun.nest.utils.cache.CacheItem;
import com.zhaofujun.nest.utils.cache.CacheProvider;

public class CacheManager {
    private static Map<String, CacheClient> cachMap = new HashMap<>();

    public static void addCacheItem(String code, String cacheProviderCode, long idleSeconds) {
        CacheProvider cacheProvider = ProviderManager.get(CacheProvider.class, cacheProviderCode);
        cachMap.put(code, new CacheClientImlp(code, cacheProvider, idleSeconds));
    }

    public static void addCacheItem(CacheItem cacheItem) {
        CacheProvider cacheProvider = ProviderManager.get(CacheProvider.class, cacheItem.getCacheProviderCode());
        cachMap.put(cacheItem.getCode(),
                new CacheClientImlp(cacheItem.getCode(), cacheProvider, cacheItem.getIdleSeconds()));
    }

    public static void addCacheItem(Collection<CacheItem> cacheItems) {
        cacheItems.forEach(cacheItem -> {
            addCacheItem(cacheItem);
        });
    }

    public static CacheClient getCacheClient(String code) {
        CacheClient cacheClient = cachMap.get(code);
        if (cacheClient == null)
            cacheClient = getCacheClient(NestConst.defaultCacheItem);
        return cacheClient;
    }
}

/**
 * 缓存客户端
 *
 * @author Jove
 */
class CacheClientImlp implements CacheClient {

    private CacheProvider cacheProvider;

    private String code;

    private long idleSeconds;

    public CacheClientImlp(String code, CacheProvider cacheProvider, long idleSeconds) {
        this.cacheProvider = cacheProvider;
        this.code = code;
        this.idleSeconds = idleSeconds;
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

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public <T> T get(Class<T> tClass, String key) {
        String objeString = cacheProvider.get(this.code, key);
        return JsonUtil.parseObject(objeString, tClass);
    }

    @Override
    public String get(String key) {
        return cacheProvider.get(this.code, key);
    }

    @Override
    public <T> Map<String, T> get(Class<T> tClass, String... keys) {
        Map<String, T> tMap = new HashMap<>();
        cacheProvider.get(code, keys).forEach((key, objecString) -> {
            T object = JsonUtil.parseObject(objecString, tClass);
            tMap.put(key, object);
        });
        return tMap;
    }

    @Override
    public void put(String key, Object value, long idleSeconds) {
        String objecString = JsonUtil.toJsonString(value);
        cacheProvider.put(code, key, objecString, idleSeconds);
    }

    @Override
    public void put(String key, String value, long idleSeconds) {
        cacheProvider.put(code, key, value, idleSeconds);
    }

    @Override
    public void put(String key, Object value) {
        String objecString = JsonUtil.toJsonString(value);
        cacheProvider.put(code, key, objecString, 12 * 60);
    }

    @Override
    public void put(String key, String value) {
        cacheProvider.put(code, key, value, idleSeconds);
    }
}