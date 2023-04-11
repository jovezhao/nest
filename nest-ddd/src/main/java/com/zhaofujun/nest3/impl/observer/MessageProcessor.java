package com.zhaofujun.nest3.impl.observer;

import com.zhaofujun.nest3.CustomException;
import com.zhaofujun.nest3.CustomExceptionable;
import com.zhaofujun.nest3.NestCustomException;
import com.zhaofujun.nest3.SystemException;
import com.zhaofujun.nest3.application.event.MessageInfo;
import com.zhaofujun.nest3.application.event.MessageRecord;
import com.zhaofujun.nest3.application.event.MessageStore;
import com.zhaofujun.nest3.application.event.RawParameterizedType;
import com.zhaofujun.nest3.application.provider.JsonProvider;
import com.zhaofujun.nest3.impl.fastjson.FastjsonProvider;
import com.zhaofujun.nest3.model.EventArgs;
import com.zhaofujun.nest3.model.EventData;
import com.zhaofujun.nest3.model.EventHandler;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;
import java.util.function.Consumer;


/**
 * 消息处理器
 * <p>
 * 通过消息中间件接收到消息后，进行消息反序列化、幂等、方法回调、异常处理。
 */
public class MessageProcessor {

    private JsonProvider jsonProvider = new FastjsonProvider();
    private EventHandler eventHandler;
    private MessageStore messageStore;

    public MessageProcessor(EventHandler eventHandler, MessageStore messageStore) {
        this.eventHandler = eventHandler;
        this.messageStore = messageStore;
    }

    protected void onReceivedMessage(String messageInfoString, Consumer<MessageInfo> beforeReceive) {

        MessageInfo messageInfo;
        try {

            messageInfo = (MessageInfo) jsonProvider.parseObject(messageInfoString, RawParameterizedType.make(MessageInfo.class, eventHandler.getEventDataClass()));
        } catch (Exception ex) {
//            logger.warn("解析消息体失败,MessageInfo：" + messageInfoString);
            throw new NestCustomException(NestCustomException.JsonAnalyseFailed, messageInfoString);
        }

//        MessageChannelProvider messageChannelProvider = MessageChannelProviderFactory.getMessageChannelProviderByEventCode(eventHandler.getEventCode());
//        NestApplication.current().onMessageReceived(messageChannelProvider, messageInfo);
        if (beforeReceive != null)
            beforeReceive.accept(messageInfo);

        String eventDataText = messageInfo.getEventData();
        String eventDataType = messageInfo.getEventDataType();
        EventData eventData=null;
        try {
            Class<?> aClass = Class.forName(eventDataType);
            eventData = (EventData) jsonProvider.parseObject(eventDataText, aClass);
        } catch (Exception exception) {
            //todo 无法反序列化
        }
        MessageRecord record = new MessageRecord();
//        record.setHandlerName(eventHandler.getClass().getName());
        record.setHandleTime(new Date());
        record.setId(UUID.randomUUID().toString());
        record.setMessageInfo(messageInfo);
        record.setReceiveTime(new Date());

        EventArgs eventArgs = new EventArgs();
//        eventArgs.setEventCode(eventHandler.getEventCode());
        eventArgs.setSource(messageInfo.getEventSource());
        eventArgs.setMessageId(messageInfo.getMessageId());
        eventArgs.setReceiveTime(LocalDateTime.now());
        eventArgs.setSendTime(messageInfo.getSendTime());


        if (messageStore.isSucceed(messageInfo.getMessageId(), eventHandler.getClass().getName()))
            return;


        try {

            eventHandler.handle(eventData, eventArgs);
            messageStore.save(record);
        } catch (CustomException ex) {
            eventHandler.onCustomException(ex);
            throw ex;
        } catch (SystemException ex) {
//            onFailed(eventHandler, context, ex);
            eventHandler.onSystemException(ex);
            throw ex;
        } catch (Exception ex) {

            if (ex instanceof CustomExceptionable) {
                //业务异常
                NestCustomException customException = new NestCustomException(NestCustomException.Other, "", ex);
                eventHandler.onCustomException(customException);
                throw customException;
            } else {

//                onFailed(eventHandler, context, ex);
                SystemException systemException = new SystemException("事件处理器发生系统异常", ex);
                eventHandler.onSystemException(systemException);
                throw systemException;
            }
        } finally {
//            onEnds(eventHandler, context);
        }
    }
}
