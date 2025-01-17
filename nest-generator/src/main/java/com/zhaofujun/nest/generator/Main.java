package com.zhaofujun.nest.generator;

import java.lang.reflect.Field;

public class Main {
    private int aa;
    public static void main(String[] args) {
        System.out.println("Hello World");
        for (Field field : Main.class.getDeclaredFields()) {
            System.out.println("name:"+field.getName());
            System.out.println("type:"+field.getType());

        }
    }
}
