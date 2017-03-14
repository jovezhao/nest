package com.ywkj.nest.core.identifier;

/**
 * Created by Jove on 2016/9/8.
 */
public interface IGeneratorStrategy {
    default String generate(){
        return generate(null);
    }
    String generate(Object object);
}
