package com.zhaofujun.nest.springboot.test;

import org.springframework.stereotype.Component;

import com.zhaofujun.nest.springboot.EventListener;

@Component
public class ListenerTest {

    @EventListener(eventName = "Test_AAA", eventDataClass = EventDto.class)
    public void testBB(EventDto eventDto) {
        System.out.println("处理2" + eventDto+Thread.currentThread().getName());

    }
}