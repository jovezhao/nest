package com.zhaofujun.nest.context.model;

import com.zhaofujun.nest.core.Identifier;

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

    public static UUIdentifier valueOf(String value) {
        return new UUIdentifier(UUID.fromString(value));
    }

    private static UUIdentifier newId() {
        return new UUIdentifier(UUID.randomUUID());
    }

}
