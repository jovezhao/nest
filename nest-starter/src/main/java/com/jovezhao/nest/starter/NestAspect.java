package com.jovezhao.nest.starter;

import com.jovezhao.nest.ddd.repository.IUnitOfWork;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by zhaofujun on 2017/6/16.
 */
@Aspect
@Component
public class NestAspect {


    @Autowired
    private IUnitOfWork unitOfWork;

    @Around("execution(public * *(..)) && @within(com.jovezhao.nest.starter.AppService)")
    public Object aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;

        try {
            result = joinPoint.proceed();
            unitOfWork.commit();

        } catch (Throwable throwable) {
            throwable.printStackTrace();
            unitOfWork.rollback();
            throw throwable;
        }
        return result;
    }
}
