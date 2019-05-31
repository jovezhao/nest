package com.guoshouxiang.nest.context.event;

import com.guoshouxiang.nest.container.BeanFinder;
import com.guoshouxiang.nest.context.event.message.MessageConverter;
import com.guoshouxiang.nest.context.event.message.MessageInfo;
import com.guoshouxiang.nest.context.event.channel.MessageChannel;
import com.guoshouxiang.nest.context.event.channel.MessageChannelFactory;
import com.guoshouxiang.nest.context.event.channel.MessageConsumer;
import com.guoshouxiang.nest.context.event.channel.MessageProducer;

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



    public void registerHandler(EventHandler eventHandler) {


        MessageChannel messageChannel = messageChannelFactory.createByEventCode(eventHandler.getEventCode());

        MessageConsumer messageConsumer = messageChannel.getMessageConsumer();
        messageConsumer.subscribe(eventHandler);
    }

    public void autoRegister() {
        beanFinder.getInstances(EventHandler.class).forEach(p -> registerHandler(p));
    }


}


