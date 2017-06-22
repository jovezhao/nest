package com.jovezhao.nest.test;

import com.jovezhao.nest.ddd.event.EventBus;
import com.jovezhao.nest.ddd.event.EventHandler;
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
        EventBus.registerHandler(new EventHandler<String>() {
            @Override
            public String getEventName() {
                return "test";
            }

            @Override
            public void handle(String data) {
                System.out.println("do "+data);
            }
        });
        EventBus.registerHandler(new EventHandler<String>() {
            @Override
            public String getEventName() {
                return "test";
            }

            @Override
            public void handle(String data) {
                System.out.println("do 22"+data);
            }
        });

        EventBus.publish("test","data");
        System.out.println(userService.changeName("no config"));
        EventBus.publish("test","data22");

    }
}
