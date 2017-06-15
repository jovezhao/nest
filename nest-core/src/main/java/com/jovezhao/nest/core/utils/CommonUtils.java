package com.jovezhao.nest.core.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 一些不能特别明确分类的工具类
 * Created by xiangxj on 2015/12/14.
 */
public class CommonUtils {

    /**
     * 验证IP地址格式
     *
     * @param ipaddr 需要验证的IP地址
     * @return true：格式正确   false：格式不正确
     */
    public static boolean isIPAddress(String ipaddr) {
        boolean flag = false;
        Pattern pattern = Pattern.compile("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");
        Matcher m = pattern.matcher(ipaddr);
        flag = m.matches();
        return flag;
    }
}
