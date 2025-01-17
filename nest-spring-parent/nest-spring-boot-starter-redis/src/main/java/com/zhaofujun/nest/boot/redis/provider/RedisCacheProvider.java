package com.zhaofujun.nest.boot.redis.provider;

import com.zhaofujun.nest.provider.CacheProvider;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class RedisCacheProvider implements CacheProvider {
    public final static String CODE = "REDIS_CACHE_PROVIDER";

    private StringRedisTemplate redisTemplate;

    public RedisCacheProvider(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public String getCode() {
        return CODE;
    }

    private String getRedisKey(String groupName, String key) {
        return groupName + "_" + key;
    }

    @Override
    public String get(String groupName, String key) {
        String redisKey = getRedisKey(groupName, key);
        return redisTemplate.opsForValue().get(redisKey);

    }

    @Override
    public Map<String, String> get(String groupName, String... keys) {

        Map<String, String> result = new HashMap<>();
        List<String> values = redisTemplate.opsForValue().multiGet(List.of(keys));
        if (values != null) {
            for (int i = 0; i < keys.length; i++) {
                result.put(keys[i], values.get(i));
            }
        }
        return result;
    }

    @Override
    public void put(String groupName, String key, String value, long idleSeconds) {

        String redisKey = getRedisKey(groupName, key);
        redisTemplate.opsForValue().set(redisKey, value);
        redisTemplate.expire(redisKey, idleSeconds, TimeUnit.SECONDS);
    }

    @Override
    public boolean remove(String groupName, String key) {
        String redisKey = getRedisKey(groupName, key);
        return redisTemplate.delete(redisKey);
    }

    @Override
    public void removeAll(String groupName) {

        Set<String> keys = redisTemplate.keys(groupName + "_*");
        redisTemplate.delete(keys);
    }

    @Override
    public boolean containsKey(String groupName, String key) {
        String redisKey = getRedisKey(groupName, key);
        return redisTemplate.hasKey(redisKey);
    }

    @Override
    public String[] getKeys(String groupName) {
        return redisTemplate.keys(groupName + "_*").toArray(new String[]{});
    }
}
