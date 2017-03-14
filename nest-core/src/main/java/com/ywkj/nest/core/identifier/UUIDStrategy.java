package com.ywkj.nest.core.identifier;

import java.util.UUID;

/**
 * Created by Jove on 2016/9/8.
 */
public class UUIDStrategy implements IGeneratorStrategy {

    @Override
    public String generate(Object object) {
        return UUID.randomUUID().toString().replace("-","");
    }
}
