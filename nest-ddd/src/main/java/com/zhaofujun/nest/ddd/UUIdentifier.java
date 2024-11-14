package com.zhaofujun.nest.ddd;

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


    private static UUIdentifier newId() {
        return new UUIdentifier(UUID.randomUUID());
    }

}
