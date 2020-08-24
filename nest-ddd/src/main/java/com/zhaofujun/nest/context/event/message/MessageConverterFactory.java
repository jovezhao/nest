package com.zhaofujun.nest.context.event.message;

import com.zhaofujun.nest.NestApplication;

public class MessageConverterFactory {

    public static MessageConverter create() {

        return create(NestApplication.current().getMessageConfiguration().getConverter());
    }

    public static MessageConverter create(String code) {
        MessageConverter messageConverter = NestApplication.current().getProviderManage().getMessageConverter(code);
        if (messageConverter == null)
            return create(NestApplication.current().getMessageConfiguration().getConverter());
        return messageConverter;
    }
}
