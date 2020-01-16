package com.zhaofujun.nest.context.event.message;

import com.zhaofujun.nest.core.BeanFinder;

public class MessageConverterFactory {
    private BeanFinder beanFinder;

    public MessageConverterFactory(BeanFinder beanFinder) {
        this.beanFinder = beanFinder;
    }

    public MessageConverter create() {

        MessageConverter messageConverter = beanFinder.getInstance(MessageConverter.class);

        if (messageConverter == null)
            messageConverter = new DefaultMessageConverter(beanFinder);
        return messageConverter;
    }
}
