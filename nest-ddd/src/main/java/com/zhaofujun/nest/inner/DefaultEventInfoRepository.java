package com.zhaofujun.nest.inner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.zhaofujun.nest.ddd.Identifier;
import com.zhaofujun.nest.ddd.event.EventMessage;
import com.zhaofujun.nest.ddd.event.EventInfoRepository;
import com.zhaofujun.nest.ddd.event.EventState;

public class DefaultEventInfoRepository extends EventInfoRepository {

    private static List<EventMessage<?>> eventList = new ArrayList<>();

    @Override
    public void insert(EventMessage<?> t) {
        eventList.add(t);
    }

    @Override
    public void update(EventMessage<?> t) {

    }

    @Override
    public void delete(EventMessage<?> t) {
        eventList.remove(t);
    }

    @Override
    public List<Long> getListToBeSent(int commonSize, int failSize, int maxFailTime) {
        List<Long> resuList = new ArrayList<>();
        var preList = eventList.stream()
                .filter(p -> p.getEventState().equals(EventState.preSend)
                        && p.getSendTime().isBefore(LocalDateTime.now()))
                .limit(commonSize)
                .map(p -> p.getId().getId())
                .collect(Collectors.toList());
        resuList.addAll(preList);
        var failList = eventList.stream()
                .filter(p -> p.getEventState().equals(EventState.sendFail)
                        && p.getSendTime().isBefore(LocalDateTime.now()))
                .limit(failSize)
                .map(p -> p.getId().getId())
                .collect(Collectors.toList());
        resuList.addAll(failList);
        return resuList;
    }

    @Override
    public EventMessage<?> getEntityById(Class<? extends EventMessage<?>> tClass, Identifier identifier) {
        return eventList.stream()
                .filter(p -> p.getClass().equals(tClass) && p.getId().equals(identifier))
                .findFirst()
                .orElse(null);
    }

}
