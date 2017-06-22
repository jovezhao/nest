package com.jovezhao.nest.ddd.event;

import java.io.Serializable;

/**
 * Created by zhaofujun on 2017/6/21.
 */
public abstract class EventProducer<T extends ProviderConfig> {
    private T providerConfig;

    public T getProviderConfig() {
        return providerConfig;
    }


    public void setProviderConfig(T providerConfig) {
        this.providerConfig = providerConfig;
    }

    protected abstract void sendMessage(String eventName, Serializable object);
}
