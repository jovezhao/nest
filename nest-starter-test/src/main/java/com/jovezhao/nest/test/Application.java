package com.jovezhao.nest.test;

import com.jovezhao.nest.activemq.ActiveMQChannelProvider;
import com.jovezhao.nest.activemq.ActiveMQProviderConfig;
import com.jovezhao.nest.ddd.event.*;
import com.jovezhao.nest.test.api.UserService;
import com.jovezhao.nest.utils.SpringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.Serializable;

/**
 * Created by zhaofujun on 2017/6/17.
 */
@SpringBootApplication
public class Application implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    private UserService userService;

    @Override
    public void run(String[] args) throws Exception {
        ChannelProvider channelProvider = new ActiveMQChannelProvider();
        ActiveMQProviderConfig providerConfig = new ActiveMQProviderConfig();
        providerConfig.setBrokers("tcp://127.0.0.1:61616");
        channelProvider.setProviderConfig(providerConfig);

        EventChannelItem eventChannelItem = new EventChannelItem();
        eventChannelItem.setEventName("event1");
        eventChannelItem.setChannelProvider(channelProvider);
        EventChannelManager.put(eventChannelItem);


        EventBus.registerHandler(new EventHandler<String>() {

            @Override
            public String getEventName() {
                return "event1";
            }

            @Override
            public void handle(String data) throws Exception {
                System.out.println("fffffff"+data);
            }
        });

        userService.changeName("new name");

    }
}
