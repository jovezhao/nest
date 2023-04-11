package com.zhaofujun.nest3.impl.fastjson;

import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.reader.ObjectReader;
import com.alibaba.fastjson2.writer.ObjectWriter;
import com.zhaofujun.nest3.model.Entity;
import com.zhaofujun.nest3.model.JsonCallback;

import java.lang.reflect.Type;


public class FastjsonUtils {
    public static String toJsonString(Entity entity, JsonCallback<Entity> jsonCallback){
        JSONWriter.Context writerContext = new JSONWriter.Context(new EntityObjectWriterProvider(jsonCallback));
        writerContext.config(JSONWriter.Feature.FieldBased, JSONWriter.Feature.WriteClassName, JSONWriter.Feature.NotWriteRootClassName);
        try (JSONWriter writer = JSONWriter.of(writerContext)) {
            if (entity == null) {
                writer.writeNull();
            } else {
                Class<?> valueClass = entity.getClass();
                ObjectWriter objectWriter = writer.getObjectWriter(valueClass, valueClass);
                writer.setRootObject(entity);
                objectWriter.write(writer, entity, null, null, 0);
            }
            return writer.toString();
        }
    }

    public static <T extends Entity> T parseObject(String text, Type clazz) {

//
        if (text == null || text.isEmpty()) {
            return null;
        }
        JSONReader.Context readerContext = new JSONReader.Context(new EntityObjectReaderProvider());
        readerContext.config(JSONReader.Feature.FieldBased,JSONReader.Feature.SupportAutoType);
        try (JSONReader reader = JSONReader.of(readerContext, text)) {
            if (reader.nextIfNull()) return null;
            ObjectReader<T> objectReader = reader.getObjectReader(clazz);
            T object = objectReader.readObject(reader);
            reader.handleResolveTasks(object);
            return object;
        }
    }
}

