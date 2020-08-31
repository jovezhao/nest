package com.zhaofujun.nest.context.event.channel;

import com.zhaofujun.nest.NestApplication;
import com.zhaofujun.nest.event.ApplicationEvent;
import com.zhaofujun.nest.event.ApplicationListener;

public class MessageChannelApplicationListener implements ApplicationListener {
    @Override
    public void applicationStarted(ApplicationEvent applicationEvent) {
        NestApplication.current().getProviderManage().getMessageChannels().forEach(messageChannelProvider -> {
            messageChannelProvider.start();
        });
    }

    @Override
    public void applicationClosed(ApplicationEvent applicationEvent) {
        NestApplication.current().getProviderManage().getMessageChannels().forEach(messageChannelProvider -> {
            messageChannelProvider.close();
        });
    }
}
