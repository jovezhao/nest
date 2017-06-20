package com.jovezhao.nest.ddd.identifier;

import com.jovezhao.nest.ddd.Identifier;
import com.jovezhao.nest.ddd.StringIdentifier;

import java.util.UUID;

/**
 * Created by Jove on 2016/9/8.
 */
public class UUIDStrategy implements IdGeneratorStrategy {


    @Override
    public Identifier generate(Class clazz, Object object) {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String id = clazz.getName().charAt(0) + uuid;
        return new StringIdentifier(id);
    }
}
