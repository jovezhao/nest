package com.jovezhao.nest.utils;


import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 字符串与List之间转换工具
 * Created by xiangxj on 2016/1/14.
 */
public class ListUtils {

    /**
     * 字符串List转字符串
     *
     * @param list 字符串List
     * @return 字符串
     */
    public static String listStringToString(List<String> list) {
        //
        if ((null == list) || (list.isEmpty())) {
            return null;
        }
        //
        StringBuilder builder = new StringBuilder();
        for (String str : list) {
            builder.append(str).append(",");
        }
        return builder.toString();
    }


    /**
     * 字符串转字符串List
     *
     * @param str 字符串
     * @return 字符串List
     */
    public static List<String> stringToListString(String str) {
        //
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        List<String> list = new ArrayList<String>();
        String[] ids = str.split("\\,");
        for (String id : ids) {
            list.add(id);
        }
        return list;
    }


}
