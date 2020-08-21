package com.zhaofujun.nest.context.event.channel;

import com.zhaofujun.nest.*;
import com.zhaofujun.nest.context.event.message.*;
import com.zhaofujun.nest.context.event.store.MessageStoreProvider;
import com.zhaofujun.nest.standard.EventArgs;
import com.zhaofujun.nest.standard.EventHandler;
import com.zhaofujun.nest.standard.CustomException;
import com.zhaofujun.nest.exception.CustomExceptionable;
import com.zhaofujun.nest.exception.OtherCustomException;
import com.zhaofujun.nest.standard.SystemException;
import com.zhaofujun.nest.standard.EventData;

import java.util.Date;
import java.util.UUID;

public abstract class AbstractMessageConsumer implements MessageConsumer {

    private MessageConverter messageConverter=NestApplication.current().getProviderManage().getMessageConverter(NestApplication.current().getConfiguration().getDefaultMessageConverter());


    public MessageConverter getMessageConverter() {
        return messageConverter;
    }


    public abstract void subscribe(EventHandler eventHandler);

    protected void onReceivedMessage(MessageInfo messageInfo, EventHandler eventHandler, Object context) {
        EventData eventData = messageInfo.getData();

        MessageRecord record = new MessageRecord();
        record.setHandlerName(eventHandler.getClass().getName());
        record.setHandleTime(new Date());
        record.setId(UUID.randomUUID().toString());
        record.setMessageInfo(messageInfo);
        record.setReceiveTime(new Date());

        EventArgs eventArgs = new EventArgs();
        eventArgs.setEventCode(eventHandler.getEventCode());
        eventArgs.setSource(messageInfo.getEventSource());
        eventArgs.setMessageId(messageInfo.getMessageId());
        eventArgs.setReceiveTime(new Date());
        eventArgs.setSendTime(messageInfo.getSendTime());



        MessageStoreProvider messageStoreProvider = NestApplication.current().getProviderManage().getMessageStore(NestApplication.current().getConfiguration().getDefaultMessageStore());
        if (messageStoreProvider.isSucceed(messageInfo.getMessageId(), eventHandler.getClass().getName()))
            return;


        try {

            eventHandler.handle(eventData, eventArgs);
            messageStoreProvider.save(record);
        } catch (CustomException ex) {
            eventHandler.onCustomException(context, ex);
        } catch (SystemException ex) {
            onFailed(eventHandler, context, ex);
            eventHandler.onSystemException(context, ex);
            throw ex;
        } catch (Exception ex) {

            if (ex instanceof CustomExceptionable) {
                //业务异常
                OtherCustomException customException = new OtherCustomException("发生业务异常", ex);
                eventHandler.onCustomException(context, customException);
            } else {

                onFailed(eventHandler, context, ex);
                SystemException systemException = new SystemException("事件处理器发生系统异常", ex);
                eventHandler.onSystemException(context, systemException);
                throw systemException;
            }
        } finally {
            onEnds(eventHandler, context);
        }
    }


    protected void onFailed(EventHandler eventHandler, Object context, Exception ex) {
    }

    protected void onEnds(EventHandler eventHandler, Object context) {
    }
}
