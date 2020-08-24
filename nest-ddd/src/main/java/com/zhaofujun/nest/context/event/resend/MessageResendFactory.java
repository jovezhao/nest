package com.zhaofujun.nest.context.event.resend;

import com.zhaofujun.nest.NestApplication;

public class MessageResendFactory {

    public static MessageResendStore create() {
        return create(NestApplication.current().getMessageConfiguration().getResendStore());
    }

    public static MessageResendStore create(String code) {
        MessageResendStore messageResendStore = NestApplication.current().getProviderManage().getMessageResendStore(code);
        if (messageResendStore == null)
            return create(NestApplication.current().getMessageConfiguration().getResendStore());
        return messageResendStore;
    }
}