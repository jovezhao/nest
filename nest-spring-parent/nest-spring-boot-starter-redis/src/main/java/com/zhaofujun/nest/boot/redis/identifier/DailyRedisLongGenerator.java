package com.zhaofujun.nest.boot.redis.identifier;

import com.zhaofujun.nest.provider.LongGenerator;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.concurrent.TimeUnit;

public class DailyRedisLongGenerator implements LongGenerator {

    public final static String CODE = "DailyRedisLongGenerator";

    private RedisTemplate redisTemplate;
    private String prefix="nest_redis_id_daily_";

    public DailyRedisLongGenerator(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public String getCode() {
        return CODE;
    }

    @Override
    public Long nextValue(String key) {
        Long expireTime = LocalDate.now().atTime(23, 59, 59, 999).toInstant(ZoneOffset.of("+8")).toEpochMilli() - System.currentTimeMillis();
        redisTemplate.expire(prefix+key, expireTime, TimeUnit.MILLISECONDS);
        return redisTemplate.opsForValue().increment(prefix+key);
    }
}
