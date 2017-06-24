package com.jovezhao.nest.utils;

import com.jovezhao.nest.exception.SystemException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SpringUtils {

    private volatile static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext applicationContext) {
        SpringUtils.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        if (SpringUtils.applicationContext == null)
            throw new SystemException("SpringUtils未初始化，请调用SpringUtils.setApplicationContext()方法初始化");
        return SpringUtils.applicationContext;
    }

    public static <T> T getInstance(Class<T> beanType, String bean) {

        return getApplicationContext().getBean(bean, beanType);

    }

    public static <T> T getInstance(Class<T> beanType) {
        return getApplicationContext().getBean(beanType);
    }


    public static <T> Set<T> getInstances(Class<T> beanType) {
        return new HashSet<T>(getApplicationContext().getBeansOfType(beanType).values());
    }

    public static <T> Map<String, T> getBeansOfType(Class<T> beanType) throws BeansException {
        return getApplicationContext().getBeansOfType(beanType);
    }


}
