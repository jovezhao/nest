package com.zhaofujun.nest.test;


import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.zhaofujun.nest.json.EntityTypeAdapterFactory;
import com.zhaofujun.nest.json.ParameterizedTypeFactory;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestGson {
    @Test
    public void gsonTest() {

        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new EntityTypeAdapterFactory())
                .create();

        AdminUser user = new AdminUser();
        user.setAdminId(1);
        user.setUserId(11);

        Teacher teacher = new Teacher();
        teacher.setTid(111);
        teacher.setUser(user);

        String json = gson.toJson(teacher);

        Teacher teacher1 = gson.fromJson(json, Teacher.class);
    }

    @Test
    public void testMessage() {

        Gson gson = new GsonBuilder().create();

        AdminUser adminUser = new AdminUser();
        adminUser.setUserId(1);

        Message message = new Message();
        message.setMessageId(11);
        message.setUser(adminUser);

        String json = gson.toJson(message);
        TypeToken<Message<AdminUser>> typeToken = new TypeToken<Message<AdminUser>>() {
        };
        Type type = typeToken.getType();

        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);

        ParameterizedType type1 = ParameterizedTypeFactory.make(Message.class, AdminUser.class);
        Message user = gson.fromJson(json, type1);


    }

    @Test
    public void testDeserializeLocalDateTime() {
        final String timeJsonString = "\"2011-11-11T11:11:11\"";
        Gson defaultGson = new GsonBuilder().create();
        try {
            defaultGson.fromJson(timeJsonString, LocalDateTime.class);
        } catch (Exception e) {
            Assert.assertEquals(JsonSyntaxException.class, e.getClass());
        }

        Gson customGson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
            @Override
            public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ISO_DATE_TIME);
            }
        }).create();

        LocalDateTime expected = LocalDateTime.of(2011, 11, 11, 11, 11, 11);
        LocalDateTime deserializedLocalDateTime = customGson.fromJson(timeJsonString, LocalDateTime.class);
        Assert.assertEquals(expected, deserializedLocalDateTime);
    }
}
