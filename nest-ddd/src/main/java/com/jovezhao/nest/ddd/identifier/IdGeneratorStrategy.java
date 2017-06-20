package com.jovezhao.nest.ddd.identifier;


import com.jovezhao.nest.ddd.Identifier;

/**
 * Created by Jove on 2016/9/8.
 */
public interface IdGeneratorStrategy {

    Identifier generate(Class clazz,Object object);

}
