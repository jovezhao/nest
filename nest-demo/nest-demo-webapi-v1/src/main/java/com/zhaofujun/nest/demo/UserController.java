package com.zhaofujun.nest.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.zhaofujun.nest.demo.dto.UserDto;

@RestController
public class UserController {
    @GetMapping("/user/{id}")
    public UserDto getUser(@PathVariable String id) {
        return new UserDto();
    }
}
