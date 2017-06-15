package com.jovezhao.nest.core.cache;


import java.util.HashSet;
import java.util.Set;

/**
 * 缓存工厂
 * Created by Jove on 2016-04-05.
 */
public class CacheFactory {
    private Set<CacheGroupStrategy> strategies;

    public Set<CacheGroupStrategy> getStrategies() {
        return strategies;

    }

    public void setStrategies(Set<CacheGroupStrategy> strategies) {
        this.strategies = strategies;
    }

    public CacheClient getCacheClient(String groupName) {
        if (strategies == null)
            strategies = new HashSet<>();

        for (CacheGroupStrategy strategy : strategies) {
            if (groupName.equals(strategy.getName())) {
                return new CacheClient(strategy.getName(), strategy);
            }
        }

        CacheGroupStrategy defaultStrategy= new CacheGroupStrategy();
        defaultStrategy.setName("default_strategy");
        defaultStrategy.setIdleSeconds(1000);
        defaultStrategy.setProvider(new DefaultCacheProvider());
        return new CacheClient(defaultStrategy.getName(), defaultStrategy);
    }
}
