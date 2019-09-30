package com.zhaofujun.nest.activemq;

import com.zhaofujun.nest.container.BeanFinder;
import com.zhaofujun.nest.context.event.channel.distribute.DistributeMessageChannel;
import com.zhaofujun.nest.context.event.channel.distribute.DistributeMessageConsumer;
import com.zhaofujun.nest.context.event.channel.distribute.DistributeMessageProducer;

public class ActiveMQMessageChannel extends DistributeMessageChannel {

    private BeanFinder beanFinder;
    public static final String Code = "ACTIVEMQ_CHANNEL";
    private String brokers;

    private ActiveMQMessageConsumer messageConsumer = null;
    private ActiveMQMessageProducer messageProducer = null;


    @Override
    public DistributeMessageProducer getMessageProducer() {
        if (messageProducer == null)
            messageProducer = new ActiveMQMessageProducer(brokers);
        return messageProducer;
    }

    @Override
    public DistributeMessageConsumer getMessageConsumer() {

        if (messageConsumer == null)
            messageConsumer = new ActiveMQMessageConsumer(beanFinder, brokers);
        return messageConsumer;
    }

    public ActiveMQMessageChannel(BeanFinder beanFinder, String brokers) {
        this.beanFinder = beanFinder;
        this.brokers = brokers;
    }
}
