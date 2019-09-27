package com.zhaofujun.nest.rockermq;

import com.zhaofujun.nest.container.BeanFinder;
import com.zhaofujun.nest.context.event.channel.distribute.DistributeMessageChannel;
import com.zhaofujun.nest.context.event.channel.distribute.DistributeMessageConsumer;
import com.zhaofujun.nest.context.event.channel.distribute.DistributeMessageProducer;

/**
 *
 **/
public class RockerMQMessageChannel extends DistributeMessageChannel {


    private BeanFinder beanFinder;

    public static final String Code = "ROCKERMQ_CHANNEL";

    private RockerMqProperties rockerMqProperties;

    private RockerMqMessageProducer producer;

    private RockerMQMessageConsumer consumer;

    public RockerMQMessageChannel(BeanFinder beanFinder,RockerMqProperties rockerMqProperties) {
        this.beanFinder = beanFinder;
        this.rockerMqProperties=rockerMqProperties;
    }

    @Override
    public DistributeMessageProducer getMessageProducer() {
        if(null ==producer){
            producer=new RockerMqMessageProducer(this.rockerMqProperties);
        }

        return producer;
    }

    @Override
    public DistributeMessageConsumer getMessageConsumer() {

        if(null==consumer){
            consumer=new RockerMQMessageConsumer(this.beanFinder,this.rockerMqProperties);
        }

        return consumer;
    }
}
