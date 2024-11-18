package com.zhaofujun.nest.boot.redis.provider;

import org.springframework.data.redis.core.StringRedisTemplate;

import com.zhaofujun.nest.utils.lock.LockProvider;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class RedisLockProvider implements LockProvider {

    public final static String CODE = "RedisLockProvider";

    private StringRedisTemplate redisTemplate;
    private Long expireTime = 3000L;

    public RedisLockProvider(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private String prefix = "nest_redis_lock_";

    @Override
    public String getCode() {
        return CODE;
    }

    @Override
    public void lock(String name, Runnable runnable) {
        /**
         * 生成一个随机数
         * 开启循环
         * 在循环中执行setIfAbsent
         * 如果执行成功，就执行 runable，执行 runnable 后移除 key
         * 如果执行失败，否则继续循环
         * 
         */
        String requestId = UUID.randomUUID().toString();
        for (;;) {
            Boolean ret = redisTemplate.opsForValue().setIfAbsent(prefix + name, requestId, expireTime,
                    TimeUnit.MILLISECONDS);
            if (ret != null && ret.booleanValue()) {
                try {
                    runnable.run();
                } finally {
                    redisTemplate.delete(prefix + name);
                }
            } else {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.interrupted();
                }
            }

        }

    }
}
