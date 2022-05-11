package com.zhaofujun.nest.event;

import com.zhaofujun.nest.context.appservice.EntityOperateEnum;
import com.zhaofujun.nest.context.event.message.MessageBacklog;
import com.zhaofujun.nest.context.model.BaseEntity;
import com.zhaofujun.nest.standard.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ServiceContextListener extends NestEventListener {
    default void serviceCreated(ServiceEvent serviceEvent) {
    }

    default void serviceMethodStart(ServiceEvent serviceEvent, String methodName) {
    }

    default void serviceMethodEnd(ServiceEvent serviceEvent, String methodName) {
    }

    default void beforeCommit(ServiceEvent serviceEvent) {
    }

    default void beforeEntityCommit(ServiceEvent serviceEvent, Map<Repository, Map<EntityOperateEnum, Collection<BaseEntity>>> entityMaps) {

    }

    default void beforeMessageCommit(ServiceEvent serviceEvent, Set<MessageBacklog> messageSet) {

    }

    default void committed(ServiceEvent serviceEvent) {
    }

    default void serviceEnd(ServiceEvent serviceEvent) {
    }
}
