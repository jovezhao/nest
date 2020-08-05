package com.zhaofujun.nest.context.loader;

import com.zhaofujun.nest.context.ServiceContextManager;
import com.zhaofujun.nest.core.*;
import com.zhaofujun.nest.cache.CacheClientFactory;
import com.zhaofujun.nest.context.ServiceContext;
import com.zhaofujun.nest.context.model.Entity;
import com.zhaofujun.nest.utils.EntityCacheUtils;
import com.zhaofujun.nest.utils.EntityUtils;
import net.sf.cglib.beans.BeanCopier;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;


public class CacheEntityLoader<T extends Entity> implements EntityLoader<T> {
    private Class tClass;
    private CacheClient cacheClient;


    public CacheEntityLoader(Class tClass) {

        BeanFinder beanFinder = ServiceContextManager.getCurrent().getApplication().getBeanFinder();
        this.tClass = tClass;
        CacheClientFactory cacheClientFactory = new CacheClientFactory(beanFinder);
        this.cacheClient = cacheClientFactory.getCacheClient(EntityCacheUtils.getCacheCode());
    }

    public CacheEntityLoader() {
    }

    @Override
    public T create(Identifier id) {
        return create(tClass, id);
    }

    @Override
    public <U extends T> U create(Class uClass, Identifier id) {

        String cacheKey = EntityCacheUtils.getCacheKey(uClass, id);
        U u = cacheClient.get(uClass, cacheKey);

        if (u == null) return null;

        Entity entity = (Entity) toEntityObject(u);

        return (U) entity;
    }


    public Entity toEntityObject(Entity entityObject) {

        if (entityObject == null) return null;

        if (EntityUtils.isRepresented(entityObject)) return entityObject;

        Entity result = EntityCreate.create(entityObject.getClass(), entityObject.getId(), false, true);
        List<Field> fields = copyObjectAndFilterDomainObjectField(entityObject, result);//复制对象，并且找出需要处理的字段
        fields.forEach(p -> {
            setDomainObject(entityObject, p);
        });

        EntityUtils.setLoading(result, false);
        return result;
    }


    private void replaceValueObject(ValueObject valueObject) {
        if (valueObject == null) return;
        getAllField(valueObject.getClass())
                .stream()
                .filter(p -> DomainObject.class.isAssignableFrom(p.getType()))
                .forEach(p -> {
                    setDomainObject(valueObject, p);
                });
    }

    private void setDomainObject(DomainObject entityObject, Field p) {
        if (Entity.class.isAssignableFrom(p.getType())) {
            //如果是实体，使用仓储加载实体
            Entity source = (Entity) getFieldValue(p, entityObject);
            if (source != null) {
                Entity value = new RepositoryEntityLoader<>(p.getType()).create(source.getId());
                setFieldValue(entityObject, p, value);
            }
        }
        if (ValueObject.class.isAssignableFrom(p.getType())) {
            //如果是值对象，
            ValueObject valueObject = (ValueObject) getFieldValue(p, entityObject);
            replaceValueObject(valueObject);
        }
    }

    private void copyField(Field field, Object source, Object target) {
        try {
            if (source == null) return;
            field.setAccessible(true);
            field.set(target, field.get(source));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private List<Field> getAllField(Class tClass) {
        List<Field> fieldList = new ArrayList<>();
        Class aClass = tClass;
        while (aClass != null) {
            Field[] fields = aClass.getDeclaredFields();
            fieldList.addAll(Arrays.asList(fields));
            aClass = aClass.getSuperclass();
        }
        return fieldList;
    }

    private Object getFieldValue(Field field, Object source) {
        try {
            field.setAccessible(true);
            return field.get(source);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void setFieldValue(Object object, Field field, Object value) {
        try {
            field.setAccessible(true);
            field.set(object, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private List<Field> copyObjectAndFilterDomainObjectField(Object source, Object target) {
        List<Field> fieldList = new ArrayList<>();
        List<Field> result = new ArrayList<>();
        Class aClass = source.getClass();
        while (aClass != null) {
            Field[] fields = aClass.getDeclaredFields();
            fieldList.addAll(Arrays.asList(fields));
            aClass = aClass.getSuperclass();
        }
        fieldList.forEach(p -> {
            if (Modifier.isStatic(p.getModifiers()) || Modifier.isFinal(p.getModifiers()))
                return;
            if (p.getName().startsWith("_"))
                return;

            copyField(p, source, target);

            if (DomainObject.class.isAssignableFrom(p.getType())) {
                result.add(p);
            }

            if (p.getType().isArray()) {Ca

                Object[] fieldValue = (Object[]) getFieldValue(p, source);
                for (int i = 0; i < fieldValue.length; i++) {
                    if (Entity.class.isAssignableFrom(fieldValue[i].getClass())) {
                        fieldValue[i] = toEntityObject((Entity) fieldValue[i]);
                    }
                    if (ValueObject.class.isAssignableFrom(fieldValue[i].getClass())) {
                        replaceValueObject((ValueObject) fieldValue[i]);
                    }
                }
                return;
            }
            if (List.class.isAssignableFrom(p.getType())) {
                List fieldValues = (List) getFieldValue(p, source);
                for (int i = 0; i < fieldValues.size(); i++) {
                    Object o = fieldValues.get(i);
                    if (Entity.class.isAssignableFrom(o.getClass())) {
                        fieldValues.set(i, toEntityObject((Entity) o));
                    }
                    if (ValueObject.class.isAssignableFrom(o.getClass())) {
                        replaceValueObject((ValueObject) o);
                    }
                }
                return;
            }
            if (Set.class.isAssignableFrom(p.getType())) {
                Set fieldValues = (Set) getFieldValue(p, source);
                Object set = fieldValues.stream().map(q -> {
                    if (Entity.class.isAssignableFrom(q.getClass())) {
                        return toEntityObject((Entity) q);
                    }
                    if (ValueObject.class.isAssignableFrom(q.getClass())) {
                        replaceValueObject((ValueObject) q);
                        return (ValueObject) q;
                    }
                    return q;
                }).collect(Collectors.toSet());
                setFieldValue(target, p, set);
                return;
            }
        });
        return result;
    }

}
