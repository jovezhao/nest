package com.jovezhao.nest.test.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhaofujun on 2017/6/29.
 */
@RestController
public class TestController {
    @GetMapping("/hello")
    public String hello(){
        return "sss";
    }
}
