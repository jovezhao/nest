package com.zhaofujun.nest.boot.redis.provider;

import com.zhaofujun.nest.provider.CacheProvider;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class RedisHashCacheProvider implements CacheProvider {
    public final static String CODE = "REDIS_HASH_CACHE_PROVIDER";

    private StringRedisTemplate redisTemplate;

    public RedisHashCacheProvider(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public String getCode() {
        return CODE;
    }

    @Override
    public  String get(String groupName, String key) {

        Object json = redisTemplate.opsForHash().get(groupName, key);
        if (json != null) 
            return json.toString();
        return null;

    }

    @Override
    public  Map<String, String> get(String groupName, String... keys) {


        Map<String, String> result = new HashMap<>();


        for (String key : keys) {
            String t = get(groupName, key);
            if (t != null)
                result.put(key, t);
        }

        return result;
    }

    @Override
    public void put(String groupName, String key, String value, long idleSeconds) {

        redisTemplate.opsForHash().put(groupName, key, value);
        redisTemplate.expire(groupName, idleSeconds, TimeUnit.SECONDS);
    }

    @Override
    public boolean remove(String groupName, String key) {

        return redisTemplate.opsForHash().delete(groupName, key) > 0;

    }

    @Override
    public void removeAll(String groupName) {

        redisTemplate.delete(groupName);
    }

    @Override
    public boolean containsKey(String groupName, String key) {

        return redisTemplate.opsForHash().hasKey(groupName, key);
    }

    @Override
    public String[] getKeys(String groupName) {

        return redisTemplate.opsForHash().keys(groupName).toArray(new String[]{});

    }
}
