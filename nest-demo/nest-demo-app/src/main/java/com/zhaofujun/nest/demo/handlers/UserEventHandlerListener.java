package com.zhaofujun.nest.demo.handlers;

import org.springframework.stereotype.Component;
import com.zhaofujun.nest.boot.EventListener;
import com.zhaofujun.nest.demo.appservices.UserDto;

@Component
public class UserEventHandlerListener {
    @EventListener(eventDataClass = UserDto.class, eventName = "user_name_changed")
    public void userCreate(UserDto userDto) {
        System.out.println("event listener");
    }
}
