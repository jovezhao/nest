package com.zhaofujun.nest.spring;

import com.zhaofujun.nest.*;
import com.zhaofujun.nest.container.BeanFinder;
import com.zhaofujun.nest.context.ServiceContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;

@Aspect
public class NestAspect {

    @Autowired
    private BeanFinder beanFinder;

    @Around("execution(public * *(..)) && @within(com.zhaofujun.nest.spring.AppService)")
    public Object aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable{

        NestApplication application = beanFinder.getInstance(NestApplication.class);

        ServiceContext serviceContext =application.newInstance(joinPoint.getSignature().getDeclaringType());

        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (CustomException ex) {
            //业务处理异常，
            throw ex;
        } catch (SystemException ex) {
            //系统异常
            throw ex;
        } catch (Throwable ex) {
            //其它异常以系统异常抛出
            if (ex instanceof CustomExceptionable) {
                //业务处理异常
                throw ex;
            } else if (ex instanceof SystemExceptionable) {
                //系统处理异常
                throw new SystemException("系统异常", ex);

            } else {
                throw new SystemException("系统异常", ex);
            }
        }

        serviceContext.getContextUnitOfWork().commit();

        return result;
    }
}
