package com.zhaofujun.nest.test;

import com.zhaofujun.nest.ddd.ApplicationService;
import com.zhaofujun.nest.ddd.StringIdentifier;
import com.zhaofujun.nest.utils.EventUtil;

public class DefaultUserAppService implements ApplicationService {
    public UserDto create(String id,String name) {
        User user = new User(new StringIdentifier(id));
        user.setName(name);

        UserDto userDto = new UserDto();
        userDto.setId(user.getId().toValue());
        userDto.setName(user.getName());
        EventUtil.publish("user_created", userDto, 100);

        return userDto;
    }
}