package com.zhaofujun.nest.configuration;

import com.zhaofujun.nest.cache.CacheConfiguration;
import com.zhaofujun.nest.cache.DefaultCacheProvider;
import com.zhaofujun.nest.context.event.EventConfiguration;
import com.zhaofujun.nest.context.event.channel.local.LocalMessageChannel;
import com.zhaofujun.nest.utils.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class ConfigurationManager {

    private List<ConfigurationItem> itemList = new ArrayList<>();
    private MessageConfiguration messageConfiguration=new MessageConfiguration();
    private LockConfiguration lockConfiguration=new LockConfiguration();

    public MessageConfiguration getMessageConfiguration() {
        return messageConfiguration;
    }

    public LockConfiguration getLockConfiguration() {
        return lockConfiguration;
    }

    public void addConfigurationItem(ConfigurationItem... configurationItems) {
        itemList.addAll(Arrays.asList(configurationItems));
    }

    public void addConfigurationItem(Collection<ConfigurationItem> configurationItems) {
        itemList.addAll(configurationItems);
    }

    private <T extends ConfigurationItem> T get(Class<T> tClass, String code) {
        if (StringUtils.isEmpty(code)) return null;
        return (T) itemList
                .stream()
                .filter(p -> tClass.isAssignableFrom(p.getClass()) && code.equals(p.getCode()))
                .findFirst()
                .orElse(null);
    }

    public CacheConfiguration getCacheConfigurationByCode(String code) {

        CacheConfiguration cacheConfiguration = get(CacheConfiguration.class, code);

        if (cacheConfiguration == null) {
            cacheConfiguration = new CacheConfiguration();
            cacheConfiguration.setCode("defaultCacheItem");
            cacheConfiguration.setIdleSeconds(20000);
            cacheConfiguration.setName("默认缓存项");
            cacheConfiguration.setProviderCode(DefaultCacheProvider.PROVIDER_CODE);
        }
        return cacheConfiguration;
    }

    public List<CacheConfiguration> getCacheConfigurations() {
        return Collections.unmodifiableList(itemList.stream().filter(p -> p instanceof CacheConfiguration)
                .map(p -> (CacheConfiguration) p)
                .collect(Collectors.toList()));
    }

    public EventConfiguration getEventConfigurationByEventCode(String eventCode) {

        EventConfiguration eventConfiguration = get(EventConfiguration.class, eventCode);

        if (eventConfiguration == null) {
            eventConfiguration = new EventConfiguration();
            eventConfiguration.setCode("default");
            eventConfiguration.setMessageChannelCode(LocalMessageChannel.CHANNEL_CODE);
        }
        return eventConfiguration;
    }

    public List<EventConfiguration> getEventConfigurations() {
        return Collections.unmodifiableList(itemList.stream().filter(p -> p instanceof EventConfiguration)
                .map(p -> (EventConfiguration) p)
                .collect(Collectors.toList()));
    }

}

