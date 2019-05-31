package com.guoshouxiang.nest.context.event.channel;

import com.guoshouxiang.nest.configuration.ConfigurationManager;
import com.guoshouxiang.nest.configuration.EventConfiguration;
import com.guoshouxiang.nest.container.BeanFinder;
import com.guoshouxiang.nest.context.event.channel.local.LocalMessageChannel;

public class MessageChannelFactory {

    private BeanFinder beanFinder;
    private ConfigurationManager configurationManager;

    public MessageChannelFactory(BeanFinder beanFinder) {
        this.beanFinder = beanFinder;
        configurationManager = new ConfigurationManager(beanFinder);
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
