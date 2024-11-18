package com.zhaofujun.nest.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.reader.ObjectReader;
import com.alibaba.fastjson2.writer.ObjectWriter;
import com.zhaofujun.nest.ddd.Entity;
import com.zhaofujun.nest.ddd.Identifier;
import com.zhaofujun.nest.utils.json.EntityObjectReaderProvider;
import com.zhaofujun.nest.utils.json.EntityObjectWriterProvider;

import java.lang.reflect.Type;

public class JsonUtil {

    public static String toJsonString(Entity<? extends Identifier> entity) {
        JSONWriter.Context writerContext = new JSONWriter.Context(new EntityObjectWriterProvider());
        writerContext.config(JSONWriter.Feature.FieldBased, JSONWriter.Feature.WriteClassName,
                JSONWriter.Feature.NotWriteRootClassName);
        try (JSONWriter writer = JSONWriter.of(writerContext)) {
            if (entity == null) {
                writer.writeNull();
            } else {
                Class<?> valueClass = entity.getClass();
                var objectWriter = writer.getObjectWriter(valueClass, valueClass);
                writer.setRootObject(entity);
                objectWriter.write(writer, entity, null, null, 0);
            }
            return writer.toString();
        }
    }

    public static String toJsonString(Object object) {
        return JSON.toJSONString(object);
    }
    
    @SuppressWarnings("unchecked")
    public static <T> T parseObject(String text, Type clazz) {
        //
        if (text == null || text.isEmpty()) {
            return null;
        }
        JSONReader.Context readerContext = new JSONReader.Context(new EntityObjectReaderProvider());
        readerContext.config(JSONReader.Feature.FieldBased, JSONReader.Feature.SupportAutoType);
        try (JSONReader reader = JSONReader.of(readerContext, text)) {
            if (reader.nextIfNull())
                return null;
            ObjectReader<T> objectReader = reader.getObjectReader(clazz);
            T object = objectReader.readObject(reader);
            reader.handleResolveTasks(object);
            return object;
        }
    }
}
