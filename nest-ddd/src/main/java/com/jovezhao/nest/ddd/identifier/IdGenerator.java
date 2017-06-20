package com.jovezhao.nest.ddd.identifier;

import com.jovezhao.nest.ddd.Identifier;
import com.jovezhao.nest.utils.SpringUtils;

/**
 * Created by Jove on 2016/9/8.
 */
public class IdGenerator {

    private IdGeneratorStrategy strategy;

    private IdGenerator(IdGeneratorStrategy strategy) {
        this.strategy = strategy;
    }

    public static IdGenerator getInstance() {
        return getInstance("default");
    }

    public static IdGenerator getInstance(String beanName) {
        IdGeneratorStrategy idGeneratorStrategy = SpringUtils.getInstance(IdGeneratorStrategy.class, beanName);
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

