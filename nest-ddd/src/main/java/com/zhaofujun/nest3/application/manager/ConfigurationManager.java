package com.zhaofujun.nest3.application.manager;

import com.zhaofujun.nest3.application.NestApplication;
import com.zhaofujun.nest3.application.config.*;
import com.zhaofujun.nest3.utils.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class ConfigurationManager {

    private List<ConfigurationItem> itemList = new ArrayList<>();
    private MessageConfiguration messageConfiguration=new MessageConfiguration();
    private LockConfiguration lockConfiguration=new LockConfiguration();
    private NestApplication nestApplication;

    public ConfigurationManager(NestApplication nestApplication) {
        this.nestApplication=nestApplication;
    }

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

        return cacheConfiguration;
    }

    public List<CacheConfiguration> getCacheConfigurations() {
        return Collections.unmodifiableList(itemList.stream().filter(p -> p instanceof CacheConfiguration)
                .map(p -> (CacheConfiguration) p)
                .collect(Collectors.toList()));
    }

    public EventConfiguration getEventConfigurationByEventCode(String eventCode) {

        EventConfiguration eventConfiguration = get(EventConfiguration.class, eventCode);

        return eventConfiguration;
    }

    public List<EventConfiguration> getEventConfigurations() {
        return Collections.unmodifiableList(itemList.stream().filter(p -> p instanceof EventConfiguration)
                .map(p -> (EventConfiguration) p)
                .collect(Collectors.toList()));
    }

}

