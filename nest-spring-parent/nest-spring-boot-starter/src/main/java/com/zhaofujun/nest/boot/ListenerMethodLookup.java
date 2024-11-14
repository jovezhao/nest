package com.zhaofujun.nest.boot;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.MethodIntrospector.MetadataLookup;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.zhaofujun.nest.ddd.EventHandler;
import com.zhaofujun.nest.manager.EventHandlerManager;

@Component
public class ListenerMethodLookup implements BeanPostProcessor {

    @Override
    @Nullable
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> targetClass = AopProxyUtils.ultimateTargetClass(bean);
        MethodIntrospector.selectMethods(targetClass, new MetadataLookup<MethodAdapter>() {
            @Override
            @Nullable
            public MethodAdapter inspect(Method method) {
                EventListener eventListener = method.getAnnotation(EventListener.class);
                if (eventListener == null)
                    return null;
                return new MethodAdapter(bean, method, eventListener);
            }

        }).forEach((p, q) -> {
            EventHandlerManager.addEventHandler(q);
        });
        return bean;
    }
}

class MethodAdapter implements EventHandler {
    private Object object;
    private Method method;
    private EventListener listener;

    public MethodAdapter(Object object, Method method, EventListener listener) {
        this.object = object;
        this.method = method;
        this.listener = listener;
    }

    @Override
    public String getEventName() {
        return listener.eventName();
    }

    @Override
    public Class getEventDataClass() {
        return listener.eventDataClass();
    }

    @Override
    public void handle(Object eventData) {

        try {
            method.invoke(object, eventData);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            //
        }
    }

}