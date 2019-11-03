package com.zhaofujun.nest.context.event.store;

import com.zhaofujun.nest.container.BeanFinder;

import java.util.Set;

public class MessageStoreFactory {
    private BeanFinder beanFinder;

    public MessageStoreFactory(BeanFinder beanFinder) {
        this.beanFinder = beanFinder;
    }

    public MessageStore create() {
        Set<MessageStore> instances = beanFinder.getInstances(MessageStore.class);
        MessageStore messageStore;
        if (instances == null||instances.size()<=1){
            messageStore = new DefaultMessageStore(beanFinder);
        }else{
            messageStore=instances.stream().filter(n->!n.getClass().equals(DefaultMessageStore.class)).findFirst().orElse(null);
        }
        return messageStore;
    }
}
