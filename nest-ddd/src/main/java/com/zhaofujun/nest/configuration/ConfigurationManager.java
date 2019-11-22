package com.zhaofujun.nest.configuration;

import com.zhaofujun.nest.NestApplication;
import com.zhaofujun.nest.cache.provider.DefaultCacheProvider;
import com.zhaofujun.nest.core.BeanFinder;
import com.zhaofujun.nest.context.event.channel.local.LocalMessageChannel;

import java.util.HashMap;
import java.util.Map;

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


    public CacheConfiguration getCacheConfigurationByCode(String code) {

        CacheConfiguration cacheConfiguration = cacheConfigurations.get(code);
        if (cacheConfiguration == null) {
            cacheConfiguration = beanFinder.getInstances(CacheConfiguration.class)
                    .stream()
                    .filter(p -> p.getCacheCode().equals(code))
                    .findFirst()
                    .orElse(null);
        }
        if (cacheConfiguration == null) {
            cacheConfiguration = new CacheConfiguration();
            cacheConfiguration.setCacheCode("defaultCacheItem");
            cacheConfiguration.setIdleSeconds(20000);
            cacheConfiguration.setName("默认缓存项");
            cacheConfiguration.setProviderCode(DefaultCacheProvider.PROVIDER_CODE);
        }
        return cacheConfiguration;
    }

    public void register(CacheConfiguration cacheConfiguration) {
        cacheConfigurations.put(cacheConfiguration.getCacheCode(), cacheConfiguration);
    }

    public EventConfiguration getEventConfigurationByEventCode(String eventCode) {

        EventConfiguration eventConfiguration = eventConfigurations.get(eventCode);
        if (eventConfiguration == null) {
            eventConfiguration = beanFinder.getInstances(EventConfiguration.class)
                    .stream()
                    .filter(p -> p.getEventCode().equals(eventCode))
                    .findFirst()
                    .orElse(null);
        }
        if(eventConfiguration==null){
            eventConfiguration=new EventConfiguration();
            eventConfiguration.setEventCode("default");
            eventConfiguration.setMessageChannelCode(LocalMessageChannel.CHANNEL_CODE);
        }
        return eventConfiguration;
    }

    public void register(EventConfiguration eventConfiguration) {
        eventConfigurations.put(eventConfiguration.getEventCode(), eventConfiguration);
    }

}

