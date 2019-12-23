package com.zhaofujun.nest.test;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Test;

public class TestGson {
    @Test
    public void gsonTest() {

        Gson gson = new GsonBuilder()
//                .registerTypeAdapter(User.class, new UserAdapter())
                .registerTypeAdapterFactory(new TEntity())
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
}
