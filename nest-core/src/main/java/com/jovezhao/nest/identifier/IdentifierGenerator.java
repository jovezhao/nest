package com.jovezhao.nest.identifier;

/**
 * Created by Jove on 2016/9/8.
 */
public class IdentifierGenerator {

    public void setStrategy(IGeneratorStrategy strategy) {
        this.strategy = strategy;
    }

    private IGeneratorStrategy strategy;


    public String generate(Class clazz) {
        if (strategy == null) strategy = new UUIDStrategy();
        String id = clazz.getSimpleName().charAt(0) + strategy.generate();
        return id;
    }

    public String generate(Class clazz,Object object) {
        if (strategy == null) strategy = new UUIDStrategy();
        String id = clazz.getSimpleName().charAt(0) + strategy.generate(object);
        return id;
    }

}

