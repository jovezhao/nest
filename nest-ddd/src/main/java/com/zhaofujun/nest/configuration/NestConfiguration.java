package com.zhaofujun.nest.configuration;

import com.zhaofujun.nest.cache.CacheConfiguration;
import com.zhaofujun.nest.context.event.EventConfiguration;
import com.zhaofujun.nest.context.event.message.DefaultMessageConverter;
import com.zhaofujun.nest.context.event.resend.DefaultMessageResendStore;
import com.zhaofujun.nest.context.event.store.DefaultMessageStore;

import java.util.ArrayList;
import java.util.List;

public class NestConfiguration {
    private String defaultMessageStore = DefaultMessageStore.CACHE_CODE;
    private String defaultMessageConverter = DefaultMessageConverter.CODE;
    private String defaultMessageResendStore = DefaultMessageResendStore.CODE;
    private List<CacheConfiguration> cacheConfigurations=new ArrayList<>();
    private List<EventConfiguration> eventConfigurations=new ArrayList<>();

    public String getDefaultMessageStore() {
        return defaultMessageStore;
    }

    public String getDefaultMessageConverter() {
        return defaultMessageConverter;
    }

    public String getDefaultMessageResendStore() {
        return defaultMessageResendStore;
    }

    public void setDefaultMessageStore(String defaultMessageStore) {
        this.defaultMessageStore = defaultMessageStore;
    }

    public void setDefaultMessageConverter(String defaultMessageConverter) {
        this.defaultMessageConverter = defaultMessageConverter;
    }

    public void setDefaultMessageResendStore(String defaultMessageResendStore) {
        this.defaultMessageResendStore = defaultMessageResendStore;
    }

    public List<CacheConfiguration> getCacheConfigurations() {
        return cacheConfigurations;
    }

    public void setCacheConfigurations(List<CacheConfiguration> cacheConfigurations) {
        this.cacheConfigurations = cacheConfigurations;
    }

    public List<EventConfiguration> getEventConfigurations() {
        return eventConfigurations;
    }

    public void setEventConfigurations(List<EventConfiguration> eventConfigurations) {
        this.eventConfigurations = eventConfigurations;
    }
}
