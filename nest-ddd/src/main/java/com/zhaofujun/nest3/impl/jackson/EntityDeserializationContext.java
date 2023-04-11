package com.zhaofujun.nest3.impl.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.DefaultDeserializationContext;
import com.fasterxml.jackson.databind.deser.DeserializerFactory;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.ser.SerializerFactory;

public class EntityDeserializationContext extends DefaultDeserializationContext {


    protected EntityDeserializationContext(DeserializerFactory df) {
        super(df, null);
    }

    protected EntityDeserializationContext(DefaultDeserializationContext src, DeserializationConfig config, JsonParser p, InjectableValues values) {
        super(src, config, p, values);
    }

    protected EntityDeserializationContext(DefaultDeserializationContext src, DeserializationConfig config) {
        super(src, config);
    }

    protected EntityDeserializationContext(DefaultDeserializationContext src, DeserializerFactory factory) {
        super(src, factory);
    }

    protected EntityDeserializationContext(DefaultDeserializationContext src) {
        super(src);
    }

    @Override
    public DefaultDeserializationContext with(DeserializerFactory factory) {
        return new EntityDeserializationContext(this, factory);
    }

    @Override
    public DefaultDeserializationContext createInstance(DeserializationConfig config, JsonParser p, InjectableValues values) {
        return new EntityDeserializationContext(this, config, p, values);
    }

    @Override
    public DefaultDeserializationContext createDummyInstance(DeserializationConfig config) {
        return new EntityDeserializationContext(this, config);
    }

}
class EntitySerializerProvider extends DefaultSerializerProvider{

    @Override
    public JsonSerializer<Object> findValueSerializer(JavaType valueType, BeanProperty property) throws JsonMappingException {
        return super.findValueSerializer(valueType, property);
    }

    @Override
    public DefaultSerializerProvider createInstance(SerializationConfig config, SerializerFactory jsf) {
        return null;
    }
}