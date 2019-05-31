package com.guoshouxiang.nest.context.event.store;

import com.guoshouxiang.nest.container.BeanFinder;

public class MessageStoreFactory {
    private BeanFinder beanFinder;

    public MessageStoreFactory(BeanFinder beanFinder) {
        this.beanFinder = beanFinder;
    }

    public MessageStore create() {
        MessageStore messageStore = beanFinder.getInstance(MessageStore.class);
        if (messageStore == null)
            messageStore = new DefaultMessageStore();
        return messageStore;
    }
}
