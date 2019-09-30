package com.zhaofujun.nest.context.model;

import java.util.UUID;

public class UUIdentifier extends Identifier {
    private UUID id;

    @Override
    public String toValue() {
        return id.toString();
    }

    public UUIdentifier(UUID id) {
        this.id = id;
    }

    public static Identifier valueOf(UUID value) {
        return new UUIdentifier(value);
    }

    public static Identifier valueOf(String value) {
        return new UUIdentifier(UUID.fromString(value));
    }

    private static Identifier newId() {
        return new UUIdentifier(UUID.randomUUID());
    }

}
