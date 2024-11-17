package com.zhaofujun.nest.demo.handlers;

import org.springframework.stereotype.Component;

import com.zhaofujun.nest.ddd.EventHandler;
import com.zhaofujun.nest.demo.webapi.dto.UserDto;
@Component
public class UserEventHandler implements EventHandler<UserDto> {

    @Override
    public String getEventName() {
        return "test";
    }

    @Override
    public Class<UserDto> getEventDataClass() {
        return UserDto.class;
    }

    @Override
    public void handle(UserDto eventData) {
        System.out.println("event handler");
    }

}