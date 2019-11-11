package com.zhaofujun.nest.ioc;


import com.zhaofujun.nest.SystemException;
import com.zhaofujun.nest.ioc.annotation.AppService;
import com.zhaofujun.nest.ioc.annotation.Component;
import com.zhaofujun.nest.ioc.annotation.Autowired;
import com.zhaofujun.nest.ioc.annotation.Store;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.scanners.TypeElementsScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DefaultBeanContainer implements BeanContainer {
    Logger logger = LoggerFactory.getLogger(DefaultBeanContainer.class);
    private List<BeanTypeAndValue> beanTypeList = new ArrayList<>();

    private Reflections reflections;


    public void scan(String prefix) {
        if (reflections == null)
            reflections = new Reflections(prefix, new SubTypesScanner(false), new TypeAnnotationsScanner(), new TypeElementsScanner());
        else
            this.reflections.merge(new Reflections(prefix, new SubTypesScanner(false), new TypeAnnotationsScanner(), new TypeElementsScanner()));
    }

    public void staticInit() {
        beanTypeList.removeAll(beanTypeList.stream().filter(p -> !p.isDynamic()).collect(Collectors.toList()));
        this.reflections.getTypesAnnotatedWith(Component.class,true)
                .stream()
                .filter(p -> !p.isAnnotation())
                .forEach(p -> {
                    beanTypeList.add(createBeanTypeAndValue(p, false));
                });

        this.reflections.getTypesAnnotatedWith(AppService.class,true)
                .stream()
                .filter(p -> !p.isAnnotation())
                .forEach(p -> {
                    beanTypeList.add(createBeanTypeAndValue(p, false));
                });

        this.reflections.getTypesAnnotatedWith(Store.class,true)
                .stream()
                .filter(p -> !p.isAnnotation())
                .forEach(p -> {
                    beanTypeList.add(createBeanTypeAndValue(p, false));
                });


        inject();

    }

    @Override
    public void register(Class clazz, Object value) {
        register(clazz, "", value);
    }

    @Override
    public void register(Class clazz, String name, Object value) {
        BeanTypeAndValue typeAndValue = getBeanTypeAndValue(clazz, name);
        if (typeAndValue == null) {
            typeAndValue = new BeanTypeAndValue();
            typeAndValue.setName(name);
            typeAndValue.setClazz(clazz);
            typeAndValue.setDynamic(true);
            beanTypeList.add(typeAndValue);
        }
        typeAndValue.setValue(value);

    }


    public <T> T getInstance(Class<T> clazz) {

        BeanTypeAndValue typeAndValue = beanTypeList.stream()
                .filter(p -> p.getClazz().equals(clazz) || clazz.isAssignableFrom(p.getClazz()))
                .findFirst()
                .orElse(null);
        if (typeAndValue == null)
            return null;
        return (T) typeAndValue.getValue();
    }

    @Override
    public <T> T getInstance(Class<T> clazz, String name) {
        BeanTypeAndValue typeAndValue = beanTypeList.stream()
                .filter(p -> (p.getClazz().equals(clazz) || clazz.isAssignableFrom(p.getClazz())) && p.getName().equals(name))
                .findFirst()
                .orElse(null);
        if (typeAndValue == null)
            return null;
        return (T) typeAndValue.getValue();
    }

    public <T> Set<T> getInstances(Class<T> clazz) {
        return beanTypeList.stream()

                .filter(p -> p.getClazz().equals(clazz) || clazz.isAssignableFrom(p.getClazz()))
                .map(p -> (T) p.getValue())
                .collect(Collectors.toSet());
    }

    private BeanTypeAndValue createBeanTypeAndValue(Class<?> clazz, boolean dynamic) {
        BeanTypeAndValue typeAndValue = new DefaultBeanContainer.BeanTypeAndValue();
        typeAndValue.setClazz(clazz);
        typeAndValue.setName(getBeanName(clazz));
        typeAndValue.setValue(createServiceBean(clazz));
        typeAndValue.setDynamic(dynamic);
        return typeAndValue;
    }

    private String getBeanName(Class<?> clazz) {
        if (clazz.isAnnotationPresent(AppService.class))
            return clazz.getDeclaredAnnotation(AppService.class).value();
        if (clazz.isAnnotationPresent(Store.class))
            return clazz.getDeclaredAnnotation(Store.class).value();

        if (clazz.isAnnotationPresent(Component.class))
            return clazz.getDeclaredAnnotation(Component.class).value();
        return UUID.randomUUID().toString();
    }

    private Object createServiceBean(Class<?> clazz) {
        try {
            if (clazz.isAnnotationPresent(AppService.class)) {
                ServiceBeanBuilder beanBuilder = new ServiceBeanBuilder(this);
                return beanBuilder.create(clazz);

            }
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception ex) {
            throw new SystemException("实例化" + clazz + "时失败", ex);
        }
    }

    private BeanTypeAndValue getBeanTypeAndValue(Class clazz, String name) {
        // 类型和name都满足条件时，直接返回第一条结果
        // 类型满足条件，并且name为空，且结果只有一条数据时直接返回
        //
        List<BeanTypeAndValue> beanTypeAndValues = beanTypeList.stream()
                .filter(p -> (p.getClazz().equals(clazz) || clazz.isAssignableFrom(p.getClazz())) && p.getName().equals(name))
                .collect(Collectors.toList());
        if (beanTypeAndValues.size() > 0)
            return beanTypeAndValues.get(0);

        beanTypeAndValues = beanTypeList.stream()
                .filter(p -> (p.getClazz().equals(clazz) || clazz.isAssignableFrom(p.getClazz())) && name.equals(""))
                .collect(Collectors.toList());
        if (beanTypeAndValues.size() == 1)
            return beanTypeAndValues.get(0);
        return null;
    }

    private Object getBean(Class clazz, String name) {
        BeanTypeAndValue beanTypeAndValue = getBeanTypeAndValue(clazz, name);
        if (beanTypeAndValue == null)
            throw new SystemException("没有在容器中找到满足条件的Bean(class:" + clazz.getName() + " name:" + name + ")");
        return beanTypeAndValue.getValue();
    }


    private void inject() {
        beanTypeList.stream().forEach(p -> {
            Stream.of(p.getClazz().getDeclaredFields())
                    .filter(q -> q.isAnnotationPresent(Autowired.class))
                    .forEach(q -> {
                                try {
                                    String name = q.getDeclaredAnnotation(Autowired.class).value();
                                    Object bean = getBean((Class) q.getGenericType(), name);
                                    q.setAccessible(true);
                                    q.set(p.getValue(), bean);
                                } catch (Exception e) {
                                    logger.info("注入字段" + q.getName() + "失败", e);
                                }
                            }

                    );
        });
    }


    public class BeanTypeAndValue {
        private String name;

        private Class clazz;
        private Object value;

        private boolean dynamic;

        public boolean isDynamic() {
            return dynamic;
        }

        public void setDynamic(boolean dynamic) {
            this.dynamic = dynamic;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Class getClazz() {
            return clazz;
        }

        public void setClazz(Class clazz) {
            this.clazz = clazz;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }


    }


}
