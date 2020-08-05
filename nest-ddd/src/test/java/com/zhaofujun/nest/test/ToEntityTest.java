package com.zhaofujun.nest.test;


import com.zhaofujun.nest.context.loader.CacheEntityLoader;
import com.zhaofujun.nest.context.model.Entity;

public class ToEntityTest {
    public static void main(String[] args) {


        Teacher teacher = new Teacher();
        teacher.setTid(111);
        AdminUser adminUser = new AdminUser();
        adminUser.setAdminId(222);
//        teacher.setUser(adminUser);
//        teacher.getUserList().add(adminUser);

        Address address = new Address(1, 2, adminUser);
        teacher.getAddressList().add(address);

        CacheEntityLoader<Entity> entityCacheEntityLoader = new CacheEntityLoader<>();
        Entity entity = entityCacheEntityLoader.toEntityObject(teacher);
        entity.toString();
    }
}

