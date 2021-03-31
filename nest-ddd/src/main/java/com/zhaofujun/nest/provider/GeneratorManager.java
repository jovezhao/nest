package com.zhaofujun.nest.provider;

import com.zhaofujun.nest.provider.identifier.LocalLongGenerator;
import com.zhaofujun.nest.provider.identifier.SnowflakeLongGenerator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GeneratorManager {
    public GeneratorManager() {
        this.longGenerators = new ArrayList<>();
        this.longGenerators.add(new SnowflakeLongGenerator());
        this.longGenerators.add(new LocalLongGenerator());
    }

    private List<LongGenerator> longGenerators;


    public void addLongGenerator(Collection<LongGenerator> longGeneratorList) {
        longGenerators.addAll(longGeneratorList);
    }

    public LongGenerator getLongGenerator(String generatorName) {
        LongGenerator longGenerator = longGenerators.stream()
                .filter(p -> generatorName.equals(p.getName()))
                .findFirst()
                .get();
        if (longGenerator == null)
            return getLongGenerator("snowflakeLongGenerator");
        return longGenerator;
    }

}
