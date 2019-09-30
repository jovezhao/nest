package com.zhaofujun.nest.context.event.channel.distribute;

import com.zhaofujun.nest.container.BeanFinder;
import com.zhaofujun.nest.context.event.channel.AbstractMessageConsumer;

public abstract class DistributeMessageConsumer extends AbstractMessageConsumer {
    public DistributeMessageConsumer(BeanFinder beanFinder) {
        super(beanFinder);
    }
}
