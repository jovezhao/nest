package com.zhaofujun.nest.test;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.zhaofujun.nest.context.model.Entity;

import java.io.IOException;

public class EntityAdapter extends TypeAdapter<Entity> {
    @Override
    public void write(JsonWriter out, Entity value) throws IOException {
    }

    @Override
    public Entity read(JsonReader in) throws IOException {
        return null;
    }
}
