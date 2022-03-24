package com.zhaofujun.nest.context.event.channel;


import com.zhaofujun.nest.NestApplication;
import com.zhaofujun.nest.configuration.ConfigurationManager;
import com.zhaofujun.nest.context.event.EventConfiguration;

public class MessageChannelProviderFactory {

    public static MessageChannelProvider create(String channelCode) {
        return NestApplication.current().getProviderManage().getMessageChannel(channelCode);
    }
    public static  MessageChannelProvider getMessageChannelProviderByEventCode(String eventCode){
        ConfigurationManager configurationManager = NestApplication.current().getConfigurationManager();
        EventConfiguration eventConfiguration = configurationManager.getEventConfigurationByEventCode(eventCode);
        MessageChannelProvider messageChannel = MessageChannelProviderFactory.create(eventConfiguration.getMessageChannelCode());
        return messageChannel;
    }
}
