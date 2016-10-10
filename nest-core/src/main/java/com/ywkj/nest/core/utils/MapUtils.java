package com.ywkj.nest.core.utils;

import org.dozer.Mapper;
import org.dozer.MappingException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jove on 2016/9/26.
 */
public class MapUtils {
    private static Mapper mapper;

    static {
        mapper = SpringUtils.getInstance(Mapper.class);
    }

    public static <T> T map(Object o, Class<T> aClass) {
        return mapper.map(o, aClass);
    }

    public static void map(Object o, Object o1) {
        mapper.map(o, o1);
    }

    public static <T> T map(Object o, Class<T> aClass, String s) {
        return mapper.map(o, aClass, s);
    }

    public static void map(Object o, Object o1, String s) {
        mapper.map(o, o1, s);
    }

    public static <T> List<T> mapList(List oList, Class<T> aClass) {
        List<T> lst = new ArrayList<>();
        for (Object o : oList) {
            lst.add(mapper.map(o, aClass));
        }
        return lst;
    }
}
