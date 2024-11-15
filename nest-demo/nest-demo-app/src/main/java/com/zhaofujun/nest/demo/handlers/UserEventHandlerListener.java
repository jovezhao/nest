package com.zhaofujun.nest.demo.handlers;

import org.springframework.stereotype.Component;
import com.zhaofujun.nest.boot.EventListener;
import com.zhaofujun.nest.demo.webapi.dto.UserDto;

@Component
public class UserEventHandlerListener {
    @EventListener(eventDataClass = UserDto.class, eventName = "test")
    public void userCreate(UserDto userDto) {
        System.out.println("event listener");
    }
}
