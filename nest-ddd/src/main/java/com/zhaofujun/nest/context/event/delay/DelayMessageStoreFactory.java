package com.zhaofujun.nest.context.event.delay;

import com.zhaofujun.nest.NestApplication;
import com.zhaofujun.nest.context.event.resend.MessageResendStore;

public class DelayMessageStoreFactory {

    public static DelayMessageStore create() {
        return create(NestApplication.current().getMessageConfiguration().getResendStore());
    }

    public static DelayMessageStore create(String code) {
        DelayMessageStore messageResendStore = NestApplication.current().getProviderManage().getDelayMessageStore(code);
        if (messageResendStore == null)
            return create(NestApplication.current().getMessageConfiguration().getResendStore());
        return messageResendStore;
    }
}