package com.jovezhao.nest.ddd;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Jove on 2016/9/28.
 */
public class SpringAspect {
    @Autowired
    IUnitOfWork unitOfWork;

    public Object aroundMethod(ProceedingJoinPoint joinpoint) throws Throwable {
        Object result=null;

        try {
            result = joinpoint.proceed();
            unitOfWork.commit();

        }
        catch (Throwable throwable) {
            throwable.printStackTrace();
            unitOfWork.rollback();
            throw  throwable;
        }
        return result;
    }
}
