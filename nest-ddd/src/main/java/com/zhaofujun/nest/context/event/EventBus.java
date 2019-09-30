package com.zhaofujun.nest.context.event;

import com.zhaofujun.nest.container.BeanFinder;
import com.zhaofujun.nest.context.event.message.MessageConverter;
import com.zhaofujun.nest.context.event.message.MessageInfo;
import com.zhaofujun.nest.context.event.channel.MessageChannel;
import com.zhaofujun.nest.context.event.channel.MessageChannelFactory;
import com.zhaofujun.nest.context.event.channel.MessageConsumer;
import com.zhaofujun.nest.context.event.channel.MessageProducer;

public class EventBus {
    private BeanFinder beanFinder;
    private MessageChannelFactory messageChannelFactory;

    public EventBus(BeanFinder beanFinder) {
        this.beanFinder = beanFinder;
        this.messageChannelFactory = new MessageChannelFactory(beanFinder);
    }

    public void publish(BaseEvent baseEvent) {


        MessageChannel messageChannel = messageChannelFactory.createByEventCode(baseEvent.getEventCode());
        MessageProducer messageProducer = messageChannel.getMessageProducer();
        MessageInfo messageInfo = MessageConverter.fromEvent(baseEvent);
        messageProducer.send(baseEvent.getEventCode(), messageInfo);
    }

    public void publish(EventData eventData) {

        MessageChannel messageChannel = messageChannelFactory.createByEventCode(eventData.getEventCode());
        MessageProducer messageProducer = messageChannel.getMessageProducer();
        MessageInfo messageInfo = MessageConverter.fromEvent(eventData);
        messageProducer.send(eventData.getEventCode(), messageInfo);
    }



    public void registerHandler(EventHandler eventHandler) {


        MessageChannel messageChannel = messageChannelFactory.createByEventCode(eventHandler.getEventCode());

        MessageConsumer messageConsumer = messageChannel.getMessageConsumer();
        messageConsumer.subscribe(eventHandler);
    }

    public void autoRegister() {
        beanFinder.getInstances(EventHandler.class).forEach(p -> registerHandler(p));
    }


}


