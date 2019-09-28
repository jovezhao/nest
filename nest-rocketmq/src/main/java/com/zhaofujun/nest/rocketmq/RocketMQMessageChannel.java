package com.zhaofujun.nest.rocketmq;

import com.zhaofujun.nest.container.BeanFinder;
import com.zhaofujun.nest.context.event.channel.distribute.DistributeMessageChannel;
import com.zhaofujun.nest.context.event.channel.distribute.DistributeMessageConsumer;
import com.zhaofujun.nest.context.event.channel.distribute.DistributeMessageProducer;

/**
 *
 **/
public class RocketMQMessageChannel extends DistributeMessageChannel {


    private BeanFinder beanFinder;

    public static final String Code = "ROCKERMQ_CHANNEL";

    private RocketMqProperties rockerMqProperties;

    private RocketMqMessageProducer producer;

    private RocketMQMessageConsumer consumer;

    public RocketMQMessageChannel(BeanFinder beanFinder,RocketMqProperties rockerMqProperties) {
        this.beanFinder = beanFinder;
        this.rockerMqProperties=rockerMqProperties;
    }

    @Override
    public DistributeMessageProducer getMessageProducer() {
        if(null ==producer){
            producer=new RocketMqMessageProducer(this.rockerMqProperties);
        }

        return producer;
    }

    @Override
    public DistributeMessageConsumer getMessageConsumer() {

        if(null==consumer){
            consumer=new RocketMQMessageConsumer(this.beanFinder,this.rockerMqProperties);
        }

        return consumer;
    }
}
