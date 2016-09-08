package com.ywkj.nest.core.utils;

import com.ywkj.nest.core.log.ILog;
import com.ywkj.nest.core.log.LogAdapter;
import org.springframework.context.ApplicationContext;

import java.util.HashSet;
import java.util.Set;

public class SpringUtils {
    static ILog logger = new LogAdapter(SpringUtils.class);

    private volatile static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext applicationContext) {
        SpringUtils.applicationContext = applicationContext;
    }


    public static <T> T getInstance(Class<T> beanType, String bean) {
        try {

            return applicationContext.getBean(bean,beanType);
        } catch (Exception ex) {
            logger.warn("没有找到指定的bean", bean);
            return null;
        }

    }

    public static <T, U extends T> T getInstance(Class<T> beanType, String bean, Class<U> defaultType) {
        try {
            T t = applicationContext.getBean(bean,beanType);
            if (t == null)
                t = applicationContext.getBean(defaultType);
            return t;
        } catch (Exception ex) {
            logger.warn("没有找到指定的bean", bean);
            return null;
        }

    }

    public static <T> T getInstance(Class<T> beanType) {
        return applicationContext.getBean(beanType);
    }

    /**
     * 获取指定类型的实例，当找不到指定类型的bean时使用默认类型
     * @param beanType 指定类型
     * @param defaultType 默认类型
     * @param <T>
     * @param <U>
     * @return
     */
    public static <T, U extends T> T getInstance(Class<T> beanType, Class<U> defaultType) {
        T t = applicationContext.getBean(beanType);
        if (t == null)
            t = applicationContext.getBean(defaultType);
        return t;
    }

    public static <T> Set<T> getInstances(Class<T> beanType) {
        return new HashSet<T>(applicationContext.getBeansOfType(beanType).values());
    }

    @SuppressWarnings("unchecked")
    public static <T> T getByBeanName(String beanName) {
        return (T) applicationContext.getBean(beanName);
    }
}
