package com.zhaofujun.nest.context.model;

import com.zhaofujun.nest.utils.identifier.LongIdentifierGenerator;
import com.zhaofujun.nest.utils.identifier.impl.DefaultLongIdentifierGenerator;

import java.util.ServiceLoader;

public class LongIdentifier extends AbstractIdentifier {

    private static LongIdentifierGenerator longIdentifierGenerator;

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
        if (null != longIdentifierGenerator) {
            return;
        }
        for (LongIdentifierGenerator first : ServiceLoader.load(LongIdentifierGenerator.class)) {
            if (null != longIdentifierGenerator) {
                throw new IllegalStateException("More than 1 implementations of generator are listed in META-INF/services. Consider remove other and keep only one implementation.");
            }
            longIdentifierGenerator = first;
        }
        if (null == longIdentifierGenerator) {
            longIdentifierGenerator = new DefaultLongIdentifierGenerator();
        }
    }

    public static void setLongIdentifierGenerator(final LongIdentifierGenerator longIdentifierGenerator) {
        if (null != LongIdentifier.longIdentifierGenerator) {
            throw new IllegalStateException("LongIdentifierGenerator has already initialized.");
        }
        LongIdentifier.longIdentifierGenerator = longIdentifierGenerator;
    }
}
