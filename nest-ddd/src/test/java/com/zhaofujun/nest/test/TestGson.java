package com.zhaofujun.nest.test;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.zhaofujun.nest.json.EntityTypeAdapterFactory;
import org.junit.Test;

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

        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        JsonObject user = jsonObject.getAsJsonObject("user");


    }
}
