package com.zhaofujun.nest.context.event.delay;

import com.zhaofujun.nest.NestApplication;

public class DelayMessageStoreFactory {

    public static DelayMessageStore create() {
        return create(NestApplication.current().getMessageConfiguration().getDelayStore());
    }

    public static DelayMessageStore create(String code) {
        DelayMessageStore messageDelayStore = NestApplication.current().getProviderManage().getDelayMessageStore(code);
        if (messageDelayStore == null)
            return create(NestApplication.current().getMessageConfiguration().getDelayStore());
        return messageDelayStore;
    }
}