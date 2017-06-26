package com.jovezhao.nest.test.repositories.models;

import com.jovezhao.nest.ddd.repository.IRepository;
import com.jovezhao.nest.ddd.repository.IRoleRepository;
import com.jovezhao.nest.ddd.Identifier;
import com.jovezhao.nest.ddd.builder.EntityLoader;
import com.jovezhao.nest.test.models.Teacher;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Created by zhaofujun on 2017/6/20.
 */
@Repository("Teacher_Repository")
public class TeacherRepository implements IRoleRepository<Teacher>, IRepository<Teacher> {
    @Override
    public Set<Identifier> getRoleIds(Identifier actorId) {
        return null;
    }

    @Override
    public Identifier getActorIdByRoleId(Identifier roleId) {
        return null;
    }

    @Override
    public Teacher getEntityById(Identifier id, EntityLoader<Teacher> builder) {
        Teacher teacher = builder.create(id);
        teacher.setClassName("dbName");
        return teacher;
    }

    @Override
    public void save(Teacher teacher) {
        System.out.println("teacher save");
    }

    @Override
    public void remove(Teacher teacher) {

    }
}
