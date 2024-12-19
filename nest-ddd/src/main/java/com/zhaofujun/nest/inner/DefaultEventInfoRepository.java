package com.zhaofujun.nest.inner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.zhaofujun.nest.ddd.Identifier;
import com.zhaofujun.nest.ddd.LongIdentifier;
import com.zhaofujun.nest.ddd.event.EventMessageRepository;
import com.zhaofujun.nest.ddd.event.EventState;
import com.zhaofujun.nest.ddd.event.EventMessageModel;

public class DefaultEventInfoRepository extends EventMessageRepository {

    private static List<EventMessageModel<?>> eventList = new ArrayList<>();

    @Override
    public void insert(EventMessageModel<?> t) {
        eventList.add(t);
    }

    @Override
    public void update(EventMessageModel<?> t) {

    }

    @Override
    public void delete(EventMessageModel<?> t) {
        eventList.remove(t);
    }

    @Override
    public List<Long> getListToBeSent(int commonSize, int failSize, int maxFailTime) {
        List<Long> resultList = new ArrayList<>();
        var preList = eventList.stream()
                .filter(p -> p.getEventState().equals(EventState.preSend)
                        && p.getSendTime().isBefore(LocalDateTime.now()))
                .limit(commonSize)
                .map(p -> p.getId().getId())
                .collect(Collectors.toList());
        resultList.addAll(preList);
        var failList = eventList.stream()
                .filter(p -> p.getEventState().equals(EventState.sendFail)
                        && p.getSendTime().isBefore(LocalDateTime.now()))
                .limit(failSize)
                .map(p -> p.getId().getId())
                .collect(Collectors.toList());
        resultList.addAll(failList);
        return resultList;
    }

    @Override
    public EventMessageModel<?> getEntityById(Class<EventMessageModel<?>> tClass, LongIdentifier identifier) {
        return eventList.stream()
                .filter(p -> p.getClass().equals(tClass) && p.getId().equals(identifier))
                .findFirst()
                .orElse(null);
    }

}
