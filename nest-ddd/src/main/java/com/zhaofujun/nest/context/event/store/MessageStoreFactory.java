package com.zhaofujun.nest.context.event.store;


import com.zhaofujun.nest.NestApplication;

public class MessageStoreFactory {

    public static MessageStore create() {
        return create(NestApplication.current().getMessageConfiguration().getStore());
    }

    public static MessageStore create(String code) {
        MessageStore messageStore = NestApplication.current().getProviderManage().getMessageStore(code);
        if (messageStore == null)
            return create(NestApplication.current().getMessageConfiguration().getStore());
        return messageStore;
    }
}