package com.zhaofujun.nest.springboot.test;

import org.springframework.stereotype.Component;

import com.zhaofujun.nest.ddd.EventHandler;

@Component
public class TestHandler implements EventHandler<EventDto> {

    @Override
    public String getEventName() {
        return "Test_AAA";
    }

    @Override
    public Class<EventDto> getEventDataClass() {
        return EventDto.class;
    }

    @Override
    public void handle(EventDto eventData) {
        System.out.println("处理" + eventData.toString());
    }

}
