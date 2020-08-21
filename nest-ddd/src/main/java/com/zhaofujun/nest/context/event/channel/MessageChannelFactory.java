//package com.zhaofujun.nest.context.event.channel;
//
//import com.zhaofujun.nest.core.BeanFinder;
//import com.zhaofujun.nest.context.event.channel.local.LocalMessageChannel;
//
//public class MessageChannelFactory {
//
//    private BeanFinder beanFinder;
//
//    public MessageChannelFactory(BeanFinder beanFinder) {
//        this.beanFinder = beanFinder;
//    }
//
//    public MessageChannel create(String channelCode) {
//
//        MessageChannel messageChannel = beanFinder.getInstances(MessageChannel.class).stream()
//                .filter(p -> p.getMessageChannelCode().equals(channelCode))
//                .findFirst()
//                .orElse(null);
//
//        if (messageChannel == null)
//            messageChannel = new LocalMessageChannel(beanFinder);
//        return messageChannel;
//    }
//}
