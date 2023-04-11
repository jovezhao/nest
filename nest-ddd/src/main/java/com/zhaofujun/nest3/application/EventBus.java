//package com.zhaofujun.nest3.application;
//
//import com.zhaofujun.nest3.application.context.ServiceContext;
//import com.zhaofujun.nest3.application.event.EventInfo;
//import com.zhaofujun.nest3.model.EventData;
//import com.zhaofujun.nest3.model.EventHandler;
//
//import java.time.LocalDateTime;
//import java.util.Date;
//import java.util.UUID;
//
//public class EventBus {
//    public static void registerHandler(EventHandler eventHandler) {
//    }
//
//    public static void publish(EventData eventData, long delaySecond) {
//        publish(eventData, "?", delaySecond);
//    }
//
//    public static void publish(EventData eventData) {
//        publish(eventData, "?", 0);
//    }
//
//    public static void publish(EventData eventData, String eventSource, long delaySecond) {
//        ServiceContext serviceContext=NestApplication.currentServiceContext();
//serviceContext.addEvent(new EventInfo(eventData,delaySecond,eventSource));
//        MessageChannelProvider messageChannel = MessageChannelProviderFactory.getMessageChannelProviderByEventCode(eventData.getEventCode());
//        NestApplication.current().beforeMessagePublish(messageChannel, messageInfo);
//
//        String messageString = MessageConverterFactory.create().messageToString(messageInfo);
//        if (delaySecond == 0) {
//            publish(messageInfo);
//        } else {
//            //延时事件先提交到当前上下文，待事务提交后再save到DelayMessageStore，保证事务提交成功再发送消息
//            ServiceContextManager.get().addDelayMessageBacklog(new DelayMessageBacklog(new MessageBacklog(eventData.getEventCode(), messageString, eventData.getClass().getName(), messageInfo.getMessageId()), LocalDateTime.now().plusSeconds(delaySecond)));
//        }
//    }
//}
