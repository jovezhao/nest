package com.jovezhao.nest.ddd.identifier;

import com.jovezhao.nest.ddd.Identifier;
import com.jovezhao.nest.exception.SystemException;
import com.jovezhao.nest.log.Log;
import com.jovezhao.nest.log.LogAdapter;
import com.jovezhao.nest.utils.SpringUtils;

/**
 * Created by Jove on 2016/9/8.
 */
public class IdGenerator {

    private Log log = new LogAdapter(IdGenerator.class);
    private IdGeneratorStrategy strategy;

    private IdGenerator(IdGeneratorStrategy strategy) {
        this.strategy = strategy;
    }

    public static IdGenerator getInstance() {
        return getInstance("default");
    }

    public static IdGenerator getInstance(String beanName) {
        IdGeneratorStrategy idGeneratorStrategy = null;
        try {
            idGeneratorStrategy = SpringUtils.getInstance(IdGeneratorStrategy.class, beanName);
        } catch (Exception ex){
            //从spring中获取id生成器策略失败，不处理异常
        }
        if (idGeneratorStrategy == null)
            idGeneratorStrategy = new UUIDStrategy();
        return new IdGenerator(idGeneratorStrategy);
    }

    public Identifier generate(Class clazz) {

        return strategy.generate(clazz, null);

    }

    public Identifier generate(Class clazz, Object object) {
        return strategy.generate(clazz, object);

    }

}

