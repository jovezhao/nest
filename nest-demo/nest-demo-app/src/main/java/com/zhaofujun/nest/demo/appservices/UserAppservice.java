package com.zhaofujun.nest.demo.appservices;

import com.zhaofujun.nest.boot.AppService;
import com.zhaofujun.nest.ddd.LongIdentifier;
import com.zhaofujun.nest.demo.appservices.model.User;
import com.zhaofujun.nest.utils.EntityUtil;
import com.zhaofujun.nest.utils.EventUtil;

@AppService
public class UserAppservice {
    
    public UserDto changeName(long id) {
        User user = EntityUtil.load(User.class, new LongIdentifier(id));
        user.setName("name1");

        var result = new UserDto();
        result.setId(user.getId().toValue());
        result.setName(user.getName());

        EventUtil.publish("user_name_changed", result);

        return result;
    }
}
