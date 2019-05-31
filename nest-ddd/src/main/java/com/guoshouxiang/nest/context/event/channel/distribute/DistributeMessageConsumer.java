package com.guoshouxiang.nest.context.event.channel.distribute;

import com.guoshouxiang.nest.container.BeanFinder;
import com.guoshouxiang.nest.context.event.channel.AbstractMessageConsumer;

public abstract class DistributeMessageConsumer extends AbstractMessageConsumer {
    public DistributeMessageConsumer(BeanFinder beanFinder) {
        super(beanFinder);
    }
}
