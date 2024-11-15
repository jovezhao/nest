package com.zhaofujun.nest.demo.webapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.zhaofujun.nest.demo.appservices.UserAppservice;
import com.zhaofujun.nest.demo.webapi.dto.UserDto;

@RestController
public class UserController {
    @Autowired
    private UserAppservice userAppservice;

    @GetMapping("/user/{id}")
    public UserDto getUser(@PathVariable Long id) {
        return userAppservice.getUser(id);
    }
}
