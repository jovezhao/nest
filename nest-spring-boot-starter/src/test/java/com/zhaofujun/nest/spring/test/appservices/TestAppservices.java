package com.zhaofujun.nest.spring.test.appservices;

import com.zhaofujun.nest.context.event.EventBus;
import com.zhaofujun.nest.context.model.StringIdentifier;
import com.zhaofujun.nest.context.loader.ConstructEntityLoader;
import com.zhaofujun.nest.context.loader.EntityLoader;
import com.zhaofujun.nest.context.loader.RepositoryEntityLoader;
import com.zhaofujun.nest.spring.AppService;
import com.zhaofujun.nest.spring.test.models.PasswordChangedEvent;
import com.zhaofujun.nest.spring.test.models.PasswordChangedEventData;
import com.zhaofujun.nest.spring.test.models.User;
import org.springframework.beans.factory.annotation.Autowired;

@AppService
public class TestAppservices {
    @Autowired
    EventBus eventBus;


    public void changPwd(String userName, String newPwd) {
        EntityLoader<User> entityLoader = new RepositoryEntityLoader<>(User.class);
        User user = entityLoader.create(StringIdentifier.valueOf(userName));
        user.changPwd(newPwd);
//        EventBus eventBus = new EventBus(beanFinder);

        PasswordChangedEventData eventObject = new PasswordChangedEventData("newpwd", "oldpwd", "111");

        PasswordChangedEvent passwordChangedEvent = new PasswordChangedEvent(eventObject, "");
        eventBus.publish(passwordChangedEvent);
    }


    public void createUser(String usrName, String pwd) {
        EntityLoader<User> entityLoader = new ConstructEntityLoader<>(User.class);
        User user = entityLoader.create(StringIdentifier.valueOf(usrName));
        user.changPwd(pwd);


        PasswordChangedEventData eventObject = new PasswordChangedEventData("newpwd", "oldpwd", "111");
//
        PasswordChangedEvent passwordChangedEvent = new PasswordChangedEvent(eventObject, "");
        eventBus.publish(passwordChangedEvent);
    }
}

