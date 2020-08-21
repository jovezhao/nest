//package com.zhaofujun.nest.context.event.store;
//
//import com.zhaofujun.nest.core.BeanFinder;
//
//
//public class MessageStoreFactory {
//
//
//    public MessageStoreProvider create() {
//        MessageStore messageStore = beanFinder.getInstance(MessageStore.class);
//        if (messageStore == null)
//            messageStore = new DefaultMessageStore(beanFinder);
//        return messageStore;
//    }
//}
