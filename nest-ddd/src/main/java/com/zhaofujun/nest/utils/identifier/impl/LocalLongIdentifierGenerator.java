package com.zhaofujun.nest.utils.identifier.impl;

import com.zhaofujun.nest.utils.identifier.LongIdentifierGenerator;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class LocalLongIdentifierGenerator implements LongIdentifierGenerator {

    private Map<String,AtomicLong> sequence = new HashMap<>();

    private synchronized AtomicLong getAtomicLong(String type){
        AtomicLong atomicLong=null;
        if(sequence.get(type)==null){
            atomicLong=new AtomicLong();
            sequence.put(type,atomicLong);
        }
        return atomicLong;
    }
    @Override
    public Long nextValue(String type) {
        return getAtomicLong(type).incrementAndGet();
    }
}
