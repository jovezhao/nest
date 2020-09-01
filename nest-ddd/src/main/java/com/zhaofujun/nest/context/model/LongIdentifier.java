package com.zhaofujun.nest.context.model;

import com.zhaofujun.nest.utils.identifier.LongIdentifierGenerator;
import com.zhaofujun.nest.utils.identifier.impl.DefaultLongIdentifierGenerator;

import java.util.ServiceLoader;

public class LongIdentifier extends AbstractIdentifier {

    private static LongIdentifierGenerator longIdentifierGenerator;

    private static String identifierGeneratorName = DefaultLongIdentifierGenerator.class.getName();
    
    private final Long id;

    public LongIdentifier(Long id) {
        this.id = id;
    }

    public static LongIdentifier valueOf(Long value) {
        return new LongIdentifier(value);
    }

    public static LongIdentifier newValue() {
        return valueOf(getIdentifierGenerator().nextValue());
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toValue() {
        return id.toString();
    }

    private static LongIdentifierGenerator getIdentifierGenerator() {
        if (null == longIdentifierGenerator) {
            synchronized (LongIdentifier.class) {
                if (null == longIdentifierGenerator) {
                    initGenerator();
                }
            }
        }
        return longIdentifierGenerator;
    }

    private static void initGenerator() {
        for (LongIdentifierGenerator each : ServiceLoader.load(LongIdentifierGenerator.class)) {
            if (!identifierGeneratorName.equals(each.name())) {
                continue;
            }
            if (null != longIdentifierGenerator) {
                throw new IllegalStateException(String.format("Identifier generator name [%s] duplicated.", identifierGeneratorName));
            }
            longIdentifierGenerator = each;
        }
        if (null == longIdentifierGenerator) {
            longIdentifierGenerator = new DefaultLongIdentifierGenerator();
        }
    }

    public static String getIdentifierGeneratorName() {
        return identifierGeneratorName;
    }

    public static void setIdentifierGeneratorName(final String identifierGeneratorName) {
        if (null != longIdentifierGenerator) {
            throw new IllegalStateException("LongIdentifierGenerator has already initialized.");
        }
        if (null == identifierGeneratorName) {
            throw new IllegalArgumentException("Identifier generator name cannot be null.");
        }
        LongIdentifier.identifierGeneratorName = identifierGeneratorName;
    }
}
