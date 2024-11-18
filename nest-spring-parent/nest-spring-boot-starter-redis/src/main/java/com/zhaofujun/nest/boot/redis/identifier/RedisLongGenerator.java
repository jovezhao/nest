package com.zhaofujun.nest.boot.redis.identifier;

import com.zhaofujun.nest.provider.LongGenerator;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisLongGenerator implements LongGenerator {
    public final static String CODE = "RedisLongGenerator";

    private RedisTemplate redisTemplate;
    private String prefix = "nest_redis_id_";

    public RedisLongGenerator(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public String getCode() {
        return CODE;
    }

    @Override
    public Long nextValue(String key) {

        return redisTemplate.opsForValue().increment(prefix + key);
    }
}

