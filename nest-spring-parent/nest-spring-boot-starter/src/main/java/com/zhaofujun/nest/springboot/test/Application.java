package com.zhaofujun.nest.springboot.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextStartedEvent;

import com.zhaofujun.nest.springboot.test.application.UserApplicationService;

@SpringBootApplication
public class Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Autowired
    private UserApplicationService userService;
    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void run(String... args) throws Exception {

        //
        applicationContext.publishEvent(new ContextStartedEvent(applicationContext));
        userService.create();

        // userService.change(1);
    }
}
