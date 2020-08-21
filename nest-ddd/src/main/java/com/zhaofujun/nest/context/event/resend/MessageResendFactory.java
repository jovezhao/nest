package com.zhaofujun.nest.context.event.resend;

import com.zhaofujun.nest.NestApplication;

public class MessageResendFactory {

    public static MessageResendStore create() {
        return NestApplication.current().getProviderManage().getMessageResendStore(NestApplication.current().getConfiguration().getDefaultMessageResendStore());
    }
}