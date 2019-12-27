package com.zhaofujun.nest.context.event.channel.distribute;

import com.zhaofujun.nest.core.BeanFinder;
import com.zhaofujun.nest.context.event.channel.AbstractMessageConsumer;

public abstract class DistributeMessageConsumer extends AbstractMessageConsumer {

    public DistributeMessageConsumer(BeanFinder beanFinder) {
        super(beanFinder);
    }


    public abstract void stop();
}
