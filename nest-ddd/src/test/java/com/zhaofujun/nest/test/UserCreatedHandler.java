package com.zhaofujun.nest.test;

import com.zhaofujun.nest.ddd.EventHandler;

public class UserCreatedHandler implements EventHandler<UserDto> {

    @Override
    public String getEventName() {
        return "user_created";
    }

    @Override
    public Class<UserDto> getEventDataClass() {
        return UserDto.class;
    }

    @Override
    public void handle(UserDto eventData) {
        System.out.println("接收到用户创建成功的事件：" + eventData.toString());
    }

}
