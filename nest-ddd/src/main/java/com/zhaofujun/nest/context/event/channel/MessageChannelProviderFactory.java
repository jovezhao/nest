package com.zhaofujun.nest.context.event.channel;


import com.zhaofujun.nest.NestApplication;

public class MessageChannelProviderFactory {

    public static MessageChannelProvider create(String channelCode) {
        return NestApplication.current().getProviderManage().getMessageChannel(channelCode);
    }
}
