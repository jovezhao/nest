package com.zhaofujun.nest.test.application;

import com.zhaofujun.nest.context.model.EntityFactory;
import com.zhaofujun.nest.context.model.LongIdentifier;
import com.zhaofujun.nest.standard.AppService;
import com.zhaofujun.nest.test.domain.Teacher;

@AppService
public class TeacherApplicationService {

    public void teachCreate() {
        Teacher teacher = EntityFactory.create(Teacher.class, LongIdentifier.valueOf(11L));
        teacher.init("teacher 1");
    }
}
