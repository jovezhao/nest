package com.zhaofujun.nest.configuration;

import com.zhaofujun.nest.context.event.message.DefaultMessageConverter;
import com.zhaofujun.nest.context.event.resend.DefaultMessageResendStore;
import com.zhaofujun.nest.context.event.store.DefaultMessageStore;


public class MessageConfiguration {
    private String store = DefaultMessageStore.CACHE_CODE;
    private String converter = DefaultMessageConverter.CODE;
    private String resendStore = DefaultMessageResendStore.CODE;


    public String getStore() {
        return store;
    }

    public String getConverter() {
        return converter;
    }

    public String getResendStore() {
        return resendStore;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public void setConverter(String converter) {
        this.converter = converter;
    }

    public void setResendStore(String resendStore) {
        this.resendStore = resendStore;
    }
}
