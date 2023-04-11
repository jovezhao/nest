package com.zhaofujun.nest3.application.config;

public class MessageConfiguration {
    private String store;
    private String converter;
    private String resendStore;
    private String delayStore;

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

    public String getDelayStore() {
        return delayStore;
    }

    public void setDelayStore(String delayStore) {
        this.delayStore = delayStore;
    }
}
