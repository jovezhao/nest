package com.jovezhao.nest.test;

import com.jovezhao.nest.test.api.UserService;
import com.jovezhao.nest.utils.SpringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

        System.out.println(userService.changeName("no config"));
    }
}
