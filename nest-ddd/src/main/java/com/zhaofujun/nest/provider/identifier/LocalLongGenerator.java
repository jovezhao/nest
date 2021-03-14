package com.zhaofujun.nest.provider.identifier;

import com.zhaofujun.nest.provider.LongGenerator;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class LocalLongGenerator implements LongGenerator {


    private Map<String,AtomicLong> sequence = new HashMap<>();

    private synchronized AtomicLong getAtomicLong(String type){
        AtomicLong atomicLong= sequence.get(type);
        if(atomicLong==null){
            atomicLong=new AtomicLong();
            sequence.put(type,atomicLong);
        }
        return atomicLong;
    }

    @Override
    public String getName() {
        return "localLongGenerator";
    }

    @Override
    public Long nextValue(String type) {
        return getAtomicLong(type).incrementAndGet();
    }
}
