package com.zhaofujun.nest.context.event.store;

import com.zhaofujun.nest.container.BeanFinder;

import java.util.Set;

public class MessageStoreFactory {
    private BeanFinder beanFinder;

    public MessageStoreFactory(BeanFinder beanFinder) {
        this.beanFinder = beanFinder;
    }

    public MessageStore create() {
        MessageStore messageStore = beanFinder.getInstance(MessageStore.class);
        if (messageStore == null)
            messageStore = new DefaultMessageStore(beanFinder);
        return messageStore;
    }
}
