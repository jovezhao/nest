package com.ywkj.nest.core.cache;


import com.ywkj.nest.core.exception.GeneralException;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Set;

/**
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
        if (StringUtils.isEmpty(groupName)) groupName = "default";
        for (CacheGroupStrategy strategy : strategies) {
            if (groupName.equals(strategy.getName())) {
                return new CacheClient(strategy.getName(), strategy);
            }
        }

        throw new GeneralException("没有找到指定的缓存项", groupName);
    }
}
