package com.zhaofujun.netst.rabbitmq;

import com.zhaofujun.nest.container.BeanFinder;
import com.zhaofujun.nest.context.event.channel.distribute.DistributeMessageChannel;
import com.zhaofujun.nest.context.event.channel.distribute.DistributeMessageConsumer;
import com.zhaofujun.nest.context.event.channel.distribute.DistributeMessageProducer;

/**
 *
 **/
public class RabbitMQMessageChannel extends DistributeMessageChannel {

    private BeanFinder beanFinder;

    public static final String Code = "RABBITMQ_CHANNEL";

    private RabbitMQProviderConfig rabbitMQProviderConfig;

    public RabbitMQMessageChannel(BeanFinder beanFinder,RabbitMQProviderConfig rabbitMQProviderConfig){
        this.beanFinder=beanFinder;
        this.rabbitMQProviderConfig=rabbitMQProviderConfig;
    }

    @Override
    public DistributeMessageProducer getMessageProducer() {
        RabbitMQMessageProducer rabbitMQMessageProducer=new RabbitMQMessageProducer(this.rabbitMQProviderConfig);
        return rabbitMQMessageProducer;
    }

    @Override
    public DistributeMessageConsumer getMessageConsumer() {
        RabbitMQMessageConsumer rabbitMQMessageConsumer=new RabbitMQMessageConsumer(beanFinder,this.rabbitMQProviderConfig);
        return rabbitMQMessageConsumer;
    }
}
