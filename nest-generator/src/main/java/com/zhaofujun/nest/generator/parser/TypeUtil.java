package com.zhaofujun.nest.generator.parser;

import com.zhaofujun.nest.ddd.AggregateRoot;
import com.zhaofujun.nest.ddd.Entity;
import com.zhaofujun.nest.ddd.Identifier;
import com.zhaofujun.nest.ddd.ValueObject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class TypeUtil {
    private static final Set<Class<?>> WRAPPER = new HashSet<Class<?>>();

    static {
        WRAPPER.add(Boolean.class);
        WRAPPER.add(Character.class);
        WRAPPER.add(Byte.class);
        WRAPPER.add(Short.class);
        WRAPPER.add(Integer.class);
        WRAPPER.add(Long.class);
        WRAPPER.add(Float.class);
        WRAPPER.add(Double.class);
        WRAPPER.add(Void.class);
        WRAPPER.add(String.class);
    }
    public static boolean isPrimitive(final Class<?> clazz) {
        return clazz.isPrimitive();
    }
   
    public static boolean isWrapper(Class<?> clazz) {
        return WRAPPER.contains(clazz);
    }
    public static boolean isAggregateRoot(Class<?> clazz) {
        return AggregateRoot.class.isAssignableFrom(clazz);
    }
    public static boolean isEntity(Class<?> clazz) {
        return Entity.class.isAssignableFrom(clazz);
    }
    public static boolean isIdentifier(Class<?> clazz) {
        return Identifier.class.isAssignableFrom(clazz);
    }
    public static boolean isValueObject(Class<?> clazz) {
        return ValueObject.class.isAssignableFrom(clazz);
    }
    public static boolean isVoid(Class<?> clazz) {
        return Void.class.isAssignableFrom(clazz);
    }
    public static boolean isObject(Class<?> clazz) {
        return Object.class.isAssignableFrom(clazz);
    }
    private static boolean isEnum(final Class<?> clazz) {
        return Enum.class.isAssignableFrom(clazz);
    }
    public static boolean isDate(Class<?> clazz) {
        return Date.class.isAssignableFrom(clazz)
                || Calendar.class.isAssignableFrom(clazz)
                || LocalDate.class.isAssignableFrom(clazz)
                || LocalDateTime.class.isAssignableFrom(clazz)
                || ZonedDateTime.class.isAssignableFrom(clazz);
    }
}
