package com.jovezhao.nest.redis;

import com.jovezhao.nest.cache.ICacheProvider;
import com.jovezhao.nest.utils.JsonUtils;
import com.jovezhao.nest.utils.StringUtils;
import org.apache.commons.collections.map.HashedMap;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.*;

/**
 * Created by zhaofujun on 2017/5/16.
 */
public class RedisCacheProvider implements ICacheProvider {

    private JedisPool jedisPool;

    private String getGroupKey(String groupName) {
        return groupName + "_key";
    }

    private String getKey(String groupName, String key) {
        return groupName + "-" + key;
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    @Override
    public Object get(String groupName, String key, Class clazz) {
        Object result = null;
        Jedis jedis = jedisPool.getResource();
        if (containsKey(groupName, key, jedis))
            result = JsonUtils.toObj(jedis.get(getKey(groupName, key)), clazz);
        jedis.close();
        return result;
    }

    @Override
    public Map<String, Object> get(String groupName, Class clazz, String... keys) {
        Map<String, Object> result = new HashedMap();
        Jedis jedis = jedisPool.getResource();
        for (String key : keys) {
            if (containsKey(groupName, key, jedis))
                result.put(key, JsonUtils.toObj(jedis.get(getKey(groupName, key)), clazz));
        }
        jedis.close();
        return result;
    }

    private boolean containsKey(String groupName, String key, Jedis jedis) {
        String keys = jedis.get(getGroupKey(groupName));
        if (!StringUtils.isEmpty(keys)) {
            return Arrays.asList(keys.split(",")).contains(key);
        }
        return false;
    }

    @Override
    public void put(String groupName, String key, Object value, long idleSeconds) {
        Jedis jedis = jedisPool.getResource();
        //添加key值
        String keys = jedis.get(getGroupKey(groupName));
        List<String> keyList;
        if (!StringUtils.isEmpty(keys))
            keyList = Arrays.asList(keys.split(","));
        else
            keyList = new ArrayList<>();
        if (!keyList.contains(key))
            keyList.add(key);
        keys = String.join(",", keyList);
        jedis.set(getGroupKey(groupName), keys);
        //添加值

        jedis.set(getKey(groupName, key), JsonUtils.toJsonString(value));
        jedis.close();
    }

    @Override
    public boolean remove(String groupName, String key) {
        Jedis jedis = jedisPool.getResource();
        //添加key值
        String keys = jedis.get(getGroupKey(groupName));
        List<String> keyList;
        if (!StringUtils.isEmpty(keys))
            keyList = Arrays.asList(keys.split(","));
        else
            keyList = new ArrayList<>();
        if (keyList.contains(key))
            keyList.remove(key);
        keys = String.join(",", keyList);
        jedis.set(getGroupKey(groupName), keys);

        //移出key
        jedis.del(getKey(groupName, key));
        jedis.close();
        return true;
    }

    @Override
    public void removeAll(String groupName) {
        Jedis jedis = jedisPool.getResource();
        //取出所有的key
        String keys = jedis.get(getGroupKey(groupName));
        if (!StringUtils.isEmpty(keys)) {
            for (String key : keys.split(",")) {
                //移出key
                jedis.del(getKey(groupName, key));
            }
        }
        //移出组
        jedis.del(getGroupKey(groupName));
        jedis.close();

    }

    @Override
    public boolean containsKey(String groupName, String key) {
        Jedis jedis = jedisPool.getResource();
        boolean result = containsKey(groupName, key, jedis);
        jedis.close();
        return result;
    }

    @Override
    public String[] getKeys(String groupName) {
        Jedis jedis = jedisPool.getResource();
        String keys = jedis.get(getGroupKey(groupName));
        jedis.close();
        return keys.split(",");
    }
}
