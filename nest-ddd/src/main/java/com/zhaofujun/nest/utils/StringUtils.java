package com.zhaofujun.nest.utils;

public class StringUtils {
    public static boolean isEmpty(String str) {
        if (str == null) return true;
        if (str.equals("")) return true;
        return false;
    }
}
