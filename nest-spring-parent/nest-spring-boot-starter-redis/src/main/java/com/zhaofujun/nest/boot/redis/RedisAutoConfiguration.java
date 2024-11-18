package com.zhaofujun.nest.boot.redis;

import com.zhaofujun.nest.boot.redis.identifier.DailyRedisLongGenerator;
import com.zhaofujun.nest.boot.redis.identifier.RedisLongGenerator;
import com.zhaofujun.nest.boot.redis.identifier.RedisSequenceFactory;
import com.zhaofujun.nest.boot.redis.provider.RedisCacheProvider;
import com.zhaofujun.nest.boot.redis.provider.RedisLockProvider;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
@EnableCaching
public class RedisAutoConfiguration {

    @Bean
    public RedisCacheProvider cacheProvider(StringRedisTemplate stringRedisTemplate) {
        return new RedisCacheProvider(stringRedisTemplate);
    }
    @Bean
    public RedisSequenceFactory redisSequenceFactory(StringRedisTemplate stringRedisTemplate) {
        return new RedisSequenceFactory(stringRedisTemplate);
    }
    @Bean
    public RedisLockProvider lockProvider(StringRedisTemplate stringRedisTemplate) {
        return new RedisLockProvider(stringRedisTemplate);
    }
    @Bean(RedisLongGenerator.CODE)
    public RedisLongGenerator redisLongIdentifierGenerator(RedisTemplate<?,?> redisTemplate){
        return new RedisLongGenerator(redisTemplate);
    }

    @Bean(DailyRedisLongGenerator.CODE)
    public DailyRedisLongGenerator dailyRedisLongIdentifierGenerator(RedisTemplate<?,?> redisTemplate){
        return new DailyRedisLongGenerator(redisTemplate);
    }
}