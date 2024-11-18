package com.zhaofujun.nest.ddd.event;

import com.zhaofujun.nest.Lifecycle;
import com.zhaofujun.nest.ddd.EventHandler;
import com.zhaofujun.nest.exception.CustomException;
import com.zhaofujun.nest.utils.JsonUtil;
import com.zhaofujun.nest.utils.MessageUtil;

/**
 * 消息处理器，处理从消息通道拿到的消息体，该消息体一定是 EventData 类型
 */
public class MessageProcessor<T> {
    private EventHandler<T> eventHandler;

    public MessageProcessor(EventHandler<T> eventHandler) {
        this.eventHandler = eventHandler;
    }

    private boolean idempotent(String messageId, String handlerId) {
        // TODO 通过调用Message仓储来记录处理成功的信息
        return false;
    }

    public EventData<T> toObject(String messageString) {
        //TODO 这里可能需要用泛型来反序列化
        return JsonUtil.parseObject(messageString, EventData.class);
    }

    public MessageAck invoke(EventData<T> eventObject) {

        MessageAck result = null;
        // 幂等验证
        if (idempotent(eventObject.getId(), getHandlerId())) {
            result = MessageAck.SUCCESS;
        } else {
            MessageUtil.emit(Lifecycle.Consume_Begin.name(), eventHandler);
            try {
                // 调用 eventHandler
                eventHandler.handle(eventObject.getData());
            } catch (CustomException ex) {
                eventHandler.onCustomException(eventObject, ex);
                result = MessageAck.REFUSE;
            } catch (Throwable ex) {
                eventHandler.onSystemException(eventObject, ex);
                result = MessageAck.REFUSE;
            } finally {
                MessageUtil.emit(Lifecycle.Consume_End.name(), eventHandler);
            }
        }
        return result;
    }

    public String getEventName() {
        return eventHandler.getEventName();
    }

    public String getHandlerId() {
        return eventHandler.getEventName() + "-" + eventHandler.getClass().getName();
    }

}