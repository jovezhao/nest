package com.zhaofujun.nest.context.event;

import com.zhaofujun.nest.configuration.ConfigurationManager;
import com.zhaofujun.nest.configuration.EventConfiguration;
import com.zhaofujun.nest.context.event.channel.MessageChannel;
import com.zhaofujun.nest.context.event.channel.MessageChannelFactory;
import com.zhaofujun.nest.context.event.channel.MessageConsumer;
import com.zhaofujun.nest.context.event.channel.MessageProducer;
import com.zhaofujun.nest.context.event.message.MessageConverter;
import com.zhaofujun.nest.context.event.message.MessageConverterFactory;
import com.zhaofujun.nest.context.event.message.MessageInfo;
import com.zhaofujun.nest.core.BeanFinder;
import com.zhaofujun.nest.core.EventBus;
import com.zhaofujun.nest.core.EventData;
import com.zhaofujun.nest.core.EventHandler;

import java.util.Date;
import java.util.UUID;

public class DefaultEventBus implements EventBus {
    private BeanFinder beanFinder;
    private MessageChannelFactory messageChannelFactory;
    private ConfigurationManager configurationManager;
    private MessageConverter messageConverter;

    public DefaultEventBus(BeanFinder beanFinder) {
        this.beanFinder = beanFinder;
        this.messageConverter = new MessageConverterFactory(beanFinder).create();
        this.messageChannelFactory = new MessageChannelFactory(beanFinder);
        this.configurationManager = ConfigurationManager.getCurrent(beanFinder);
    }

    public void publish(EventData eventData) {
        publish(eventData, "?");
    }

    public void publish(EventData eventData, String eventSource) {
        EventConfiguration eventConfiguration = configurationManager.getEventConfigurationByEventCode(eventData.getEventCode());


        MessageChannel messageChannel = messageChannelFactory.create(eventConfiguration.getMessageChannelCode());
        MessageProducer messageProducer = messageChannel.getMessageProducer();


        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setMessageId(UUID.randomUUID().toString());
        messageInfo.setData(eventData);
        messageInfo.setEventSource(eventSource);
        messageInfo.setSendTime(new Date());

        messageProducer.send(eventData.getEventCode(), messageInfo);

    }


    public void registerHandler(EventHandler eventHandler) {

        EventConfiguration eventConfiguration = configurationManager.getEventConfigurationByEventCode(eventHandler.getEventCode());

        MessageChannel messageChannel = messageChannelFactory.create(eventConfiguration.getMessageChannelCode());

        MessageConsumer messageConsumer = messageChannel.getMessageConsumer();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                messageConsumer.subscribe(eventHandler);
            }
        });
        thread.start();

    }

    public void autoRegister() {
        beanFinder.getInstances(EventHandler.class).forEach(p -> registerHandler(p));
    }


}
