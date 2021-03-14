package com.zhaofujun.nest.context.model;

import com.zhaofujun.nest.provider.LongGenerator;
import com.zhaofujun.nest.provider.identifier.SnowflakeLongGenerator;


public class LongIdentifier extends AbstractIdentifier {


    private final Long id;

    public LongIdentifier(Long id) {
        this.id = id;
    }

    public static LongIdentifier valueOf(Long value) {
        return new LongIdentifier(value);
    }

    static LongGenerator generator = new SnowflakeLongGenerator();

    /**
     * 使用{@link com.zhaofujun.nest.utils.LongIdentifierUtils}替代
     * @return 返回新的标识
     */
    @Deprecated
    public static LongIdentifier newValue() {
        Long nextValue = generator.nextValue("default");
        return valueOf(nextValue);
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toValue() {
        return id.toString();
    }


}
