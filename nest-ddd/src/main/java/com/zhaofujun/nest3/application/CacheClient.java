package com.zhaofujun.nest3.application;

import com.zhaofujun.nest3.application.config.CacheConfiguration;
import com.zhaofujun.nest3.application.manager.ConfigurationManager;
import com.zhaofujun.nest3.application.provider.CacheProvider;
import com.zhaofujun.nest3.application.provider.JsonProvider;
import com.zhaofujun.nest3.impl.ehcache.EhcacheProvider;
import com.zhaofujun.nest3.impl.fastjson.FastjsonProvider;
import com.zhaofujun.nest3.utils.StringUtils;

import java.lang.reflect.Type;
import java.util.Map;

public interface CacheClient {


    static CacheClient newCacheClient(String code, String cacheProviderCode, long idleSeconds, String jsonProviderCode) {
        NestApplication application = NestApplication.current();
        CacheProvider cacheProvider = application.getProviderManage().get(CacheProvider.class, cacheProviderCode);
        JsonProvider jsonProvider = application.getProviderManage().get(JsonProvider.class, jsonProviderCode);

        return new DefaultCacheClient(code, cacheProvider, idleSeconds, jsonProvider);
    }

    <T> T get(Type type, String key);

    String get(String key);


    <T> Map<String, T> get(Type type, String... keys);

    Map<String, String> get(String... keys);

    void put(String key, Object value, long idleSeconds);

    void put(String key, String value, long idleSeconds);

    void put(String key, Object value);

    void put(String key, String value);

    boolean remove(String key);

    void removeAll();

    boolean containsKey(String key);

    String[] getKeys();
}