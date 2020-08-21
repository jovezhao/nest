package com.zhaofujun.nest.context.event.message;

import com.zhaofujun.nest.NestApplication;

public class MessageConverterFactory {

    public static MessageConverter create() {

      return NestApplication.current().getProviderManage().getMessageConverter(NestApplication.current().getConfiguration().getDefaultMessageConverter());
    }
}
