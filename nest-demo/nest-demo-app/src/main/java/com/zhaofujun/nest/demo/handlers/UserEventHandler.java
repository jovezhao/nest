package com.zhaofujun.nest.demo.handlers;

import org.springframework.stereotype.Component;
import com.zhaofujun.nest.springboot.EventListener;
import com.zhaofujun.nest.demo.webapi.dto.UserDto;

@Component
public class UserEventHandler {
    @EventListener
    public void userCreate(UserDto userDto) {

    }
}
