package com.zhaofujun.nest.ddd.event;

import java.util.List;
import java.util.stream.Collectors;

import com.zhaofujun.nest.NestConst;
import com.zhaofujun.nest.ddd.LongIdentifier;
import com.zhaofujun.nest.manager.EventManager;
import com.zhaofujun.nest.manager.ProviderManager;
import com.zhaofujun.nest.utils.EntityUtil;

public class EventAppService {
    private EventInfoQuery query;

    public void setQuery(EventInfoQuery query) {
        this.query = query;
    }

    public List<LongIdentifier> getEventInfoList(int commonSize, int failSize, int maxFailTime) {
        return query.getPersend(commonSize, failSize, maxFailTime).stream()
                .map(p -> {
                    EventMessage<?> eventInfo = EntityUtil.load(EventMessage.class, new LongIdentifier(p));
                    eventInfo.readyPublish();
                    return eventInfo.getId();
                })
                .collect(Collectors.toList());
    }

    public void sendEventInfo(LongIdentifier identifier) {
        EventMessage<?> eventInfo = EntityUtil.load(EventMessage.class, identifier);

        // 根据事件名找到通道
        String channelCode = EventManager.getChannelCode(eventInfo.getEventName());
        EventChannelProvider eventChannelProvider = ProviderManager.get(EventChannelProvider.class, channelCode,
                NestConst.defaultChannel);

        EventData<? super Object> eventData = new EventData<>();
        eventData.setData(eventInfo.getEventData());
        eventData.setId(eventInfo.getId().toValue());

        try {
            eventChannelProvider.commit(eventInfo.getEventName(), eventData);
            eventInfo.publish();
        } catch (Exception ex) {
            eventInfo.fail();
        }

    }
}
