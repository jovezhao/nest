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

    private String brokers;

    private String groupName;

    private String nameSpace;

    private boolean vipChannelEnable=false;

    private RockerMqMessageProducer producer;

    private RockerMQMessageConsumer consumer;

    public RockerMQMessageChannel(BeanFinder beanFinder, String brokers, String groupName, String nameSpace, boolean vipChannelEnable) {
        this.beanFinder = beanFinder;
        this.brokers = brokers;
        this.groupName = groupName;
        this.nameSpace = nameSpace;
        this.vipChannelEnable = vipChannelEnable;
    }

    @Override
    public DistributeMessageProducer getMessageProducer() {
        if(null ==producer){
            producer=new RockerMqMessageProducer(brokers,groupName,nameSpace,vipChannelEnable);
        }

        return producer;
    }

    @Override
    public DistributeMessageConsumer getMessageConsumer() {

        if(null==consumer){
            consumer=new RockerMQMessageConsumer(beanFinder,brokers,groupName,nameSpace,vipChannelEnable);
        }

        return consumer;
    }
}
