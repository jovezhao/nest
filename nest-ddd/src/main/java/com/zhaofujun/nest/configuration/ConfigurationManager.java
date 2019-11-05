package com.zhaofujun.nest.configuration;

import com.zhaofujun.nest.NestApplication;
import com.zhaofujun.nest.cache.provider.DefaultCacheProvider;
import com.zhaofujun.nest.container.BeanFinder;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ConfigurationManager {

    private Map<String, CacheConfiguration> cacheConfigurations = new HashMap<>();
    private Map<String, EventConfiguration> eventConfigurations = new HashMap<>();
    private BeanFinder beanFinder;

    private ConfigurationManager(BeanFinder beanFinder) {
        this.beanFinder = beanFinder;
    }

    public static ConfigurationManager create(BeanFinder beanFinder) {
        ConfigurationManager configurationManager = new ConfigurationManager(beanFinder);
        return configurationManager;
    }

    public static ConfigurationManager getCurrent(BeanFinder beanFinder) {
        NestApplication nestApplication = beanFinder.getInstance(NestApplication.class);
        return nestApplication.getConfigurationManager();
    }



    /**
     * 基本配置（BeanContainerProvider）
     * 缓存组配置 CacheConfiguration
     * 事件组配置 EventConfiguration
     */
    public CacheConfiguration getCacheConfigurationByCode(String code) {

        CacheConfiguration cacheConfiguration = cacheConfigurations.get(code);
        if (cacheConfiguration == null) {
            cacheConfiguration = beanFinder.getInstances(CacheConfiguration.class)
                    .stream()
                    .filter(p -> p.getCode().equals(code))
                    .findFirst()
                    .orElse(null);
        }
        if (cacheConfiguration == null) {
            cacheConfiguration = new CacheConfiguration();
            cacheConfiguration.setCode("default");
            cacheConfiguration.setIdleSeconds(20000);
            cacheConfiguration.setName("默认缓存项");
            cacheConfiguration.setProviderCode(DefaultCacheProvider.CODE);
        }
        return cacheConfiguration;
    }

    public void register(CacheConfiguration cacheConfiguration) {
        cacheConfigurations.put(cacheConfiguration.getCode(), cacheConfiguration);
    }

    public EventConfiguration getEventConfigurationByCode(String code) {

        EventConfiguration eventConfiguration = eventConfigurations.get(code);
        if (eventConfiguration == null)
            eventConfiguration = beanFinder.getInstance(EventConfiguration.class, code);
        return eventConfiguration;
    }

    public void register(EventConfiguration eventConfiguration) {
        eventConfigurations.put(eventConfiguration.getEventCode(), eventConfiguration);
    }

}

