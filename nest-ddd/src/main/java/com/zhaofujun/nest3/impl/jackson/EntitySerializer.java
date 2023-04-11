package com.zhaofujun.nest3.impl.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.zhaofujun.nest3.model.Entity;

import java.io.IOException;

public class EntitySerializer extends JsonSerializer<Entity> {

    @Override
    public void serialize(Entity value, JsonGenerator gen, SerializerProvider serializers) throws IOException {

    }
}