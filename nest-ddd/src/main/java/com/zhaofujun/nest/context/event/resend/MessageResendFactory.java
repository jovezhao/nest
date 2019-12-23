package com.zhaofujun.nest.context.event.resend;

import com.zhaofujun.nest.context.event.store.DefaultMessageStore;
import com.zhaofujun.nest.context.event.store.MessageStore;
import com.zhaofujun.nest.core.BeanFinder;

public class MessageResendFactory {
    private BeanFinder beanFinder;

    public MessageResendFactory(BeanFinder beanFinder) {
        this.beanFinder = beanFinder;
    }

    public MessageResendStore create() {
        MessageResendStore messageResendStore = beanFinder.getInstance(MessageResendStore.class);
        if (messageResendStore == null)
            messageResendStore = new DefaultMessageResendStore(beanFinder);
        return messageResendStore;
    }
}