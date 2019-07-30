package com.guoshouxiang.nest.context.loader;


import com.guoshouxiang.nest.SystemException;
import com.guoshouxiang.nest.context.model.BaseEntity;
import com.guoshouxiang.nest.context.model.BaseRole;
import com.guoshouxiang.nest.context.model.Identifier;
import com.guoshouxiang.nest.utils.EntityUtils;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 无中生有类实体的构建
 *
 * @param <T>
 */
public class ConstructEntityLoader<T extends BaseEntity> implements EntityLoader<T>,Serializable {
    private Class<T> tClass;

    public ConstructEntityLoader(Class<T> tClass) {
        this.tClass = tClass;
    }

    public T create(Identifier id) {
        return create(tClass, id);
    }

    public <U extends T> U create(Class<U> uClass, Identifier id) {

        if (uClass.isAssignableFrom(BaseRole.class))
            throw new SystemException("角色不能直接通过实体加载器创建，请通过实体act方法扮演");
        Enhancer enhancer = new Enhancer();
        // 设置需要创建子类的类
        enhancer.setSuperclass(uClass);
        enhancer.setCallback(new EntityMethodInterceptor());
        U t = (U) enhancer.create();
        EntityUtils.represent(t, true);
        EntityUtils.setIdentifier(t, id);
        return t;
    }


    class EntityMethodInterceptor implements MethodInterceptor, Serializable {

        private Logger logger = LoggerFactory.getLogger(this.getClass());

        // 实现MethodInterceptor接口方法
        public Object intercept(Object obj, Method method, Object[] args,
                                MethodProxy proxy) throws Throwable {

            Object result = proxy.invokeSuper(obj, args);

//
            String[] withoutMethod = {
                    "hashCode", "equals", "toString", "notify", "notifyAll", "wait"
                    , "act", "findRoles", "delete"};

            if (method.getModifiers() == 1 && !Arrays.asList(withoutMethod).contains(method.getName()) && !method.getName().startsWith("get") && !method.getName().startsWith("is")) {
                Field field = BaseEntity.class.getDeclaredField("_loading");
                field.setAccessible(true);
                if (!field.getBoolean(obj)) {
                    Method method1 = BaseEntity.class.getDeclaredMethod("addToUnitOfWork");
                    method1.setAccessible(true);
                    method1.invoke(obj);
                    logger.debug("领域实体发生更改，调用方法{}", method.getName());
                }
            }


            return result;
        }

    }
}
