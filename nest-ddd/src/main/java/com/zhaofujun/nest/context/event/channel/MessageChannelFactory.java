package com.zhaofujun.nest.context.event.channel;

import com.zhaofujun.nest.configuration.ConfigurationManager;
import com.zhaofujun.nest.configuration.EventConfiguration;
import com.zhaofujun.nest.container.BeanFinder;
import com.zhaofujun.nest.context.ServiceContext;
import com.zhaofujun.nest.context.event.channel.local.LocalMessageChannel;

public class MessageChannelFactory {

    private BeanFinder beanFinder;
    private ConfigurationManager configurationManager;

    public MessageChannelFactory(BeanFinder beanFinder) {
        this.beanFinder = beanFinder;
        configurationManager = ConfigurationManager.getCurrent(beanFinder);
    }

    public MessageChannel create(String channelCode) {

        MessageChannel messageChannel = beanFinder.getInstance(MessageChannel.class, channelCode);
        if (messageChannel == null)
            messageChannel = new LocalMessageChannel(beanFinder);
        return messageChannel;
    }

    public MessageChannel createByEventCode(String eventCode) {
        EventConfiguration eventConfiguration = configurationManager.getEventConfigurationByCode(eventCode);
        if (eventConfiguration == null) return new LocalMessageChannel(beanFinder);
        return create(eventConfiguration.getMessageChannelCode());
    }
}
