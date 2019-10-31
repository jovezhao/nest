package com.zhaofujun.nest.rabbitmq;

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

    private DistributeMessageProducer distributeMessageProducer;

    private DistributeMessageConsumer distributeMessageConsumer;

    public RabbitMQMessageChannel(BeanFinder beanFinder,RabbitMQProviderConfig rabbitMQProviderConfig){
        this.beanFinder=beanFinder;
        this.rabbitMQProviderConfig=rabbitMQProviderConfig;
    }

    @Override
    public DistributeMessageProducer getMessageProducer() {
        if(null == this.distributeMessageProducer){
            this.distributeMessageProducer=new RabbitMQMessageProducer(this.rabbitMQProviderConfig);
        }
        return distributeMessageProducer;
    }

    @Override
    public DistributeMessageConsumer getMessageConsumer() {
        if(null ==this.distributeMessageConsumer){
            this.distributeMessageConsumer=new RabbitMQMessageConsumer(beanFinder,this.rabbitMQProviderConfig);
        }

        return distributeMessageConsumer;
    }
}
