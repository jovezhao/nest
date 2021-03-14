package com.zhaofujun.nest.utils;

import com.zhaofujun.nest.NestApplication;
import com.zhaofujun.nest.context.model.LongIdentifier;
import com.zhaofujun.nest.provider.LongGenerator;

public class LongIdentifierUtils {
    public static final String snowflakeCode="snowflakeLongGenerator";
    public static final String localCode="localLongGenerator";

    public static Long createLong(String generatorName, String bizType) {

        LongGenerator longGenerator = NestApplication.current().getGeneratorManager().getLongGenerator(generatorName);
        return longGenerator.nextValue(bizType);
    }

    public static LongIdentifier createLongIdentifier(String generatorName, String bizType) {
        return LongIdentifier.valueOf(createLong(generatorName, bizType));
    }

}
