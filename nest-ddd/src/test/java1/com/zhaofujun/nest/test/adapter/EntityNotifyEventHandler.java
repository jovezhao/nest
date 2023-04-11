package com.zhaofujun.nest.test.adapter;

import com.zhaofujun.nest.context.appservice.EntityNotifyEventData;
import com.zhaofujun.nest.context.event.EventHandlerAlias;
import com.zhaofujun.nest.json.JsonCreator;
import com.zhaofujun.nest.standard.EventArgs;
import com.zhaofujun.nest.standard.EventHandler;

@EventHandlerAlias("entity_notify_handler")
public class EntityNotifyEventHandler implements EventHandler<EntityNotifyEventData> {
    @Override
    public String getEventCode() {
        return EntityNotifyEventData.CODE;
    }

    @Override
    public Class<EntityNotifyEventData> getEventDataClass() {
        return EntityNotifyEventData.class;
    }

    @Override
    public void handle(EntityNotifyEventData eventData, EventArgs eventArgs) {

        System.out.println("实体发生变化：" + JsonCreator.getInstance().toJsonString(eventData));
    }
}
