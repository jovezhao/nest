package com.zhaofujun.nest.spring;

import com.zhaofujun.nest.container.BeanFinder;
import com.zhaofujun.nest.context.ServiceContext;
import com.zhaofujun.nest.CustomException;
import com.zhaofujun.nest.SystemException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;

@Aspect
public class NestAspect {

    @Autowired
    private BeanFinder beanFinder;

    @Around("execution(public * *(..)) && @within(com.zhaofujun.nest.spring.AppService)")
    public Object aroundMethod(ProceedingJoinPoint joinPoint) {


        ServiceContext serviceContext = ServiceContext.newInstance(joinPoint.getSignature().getDeclaringType(), beanFinder);

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
            throw new SystemException("系统异常", ex);
        }

        serviceContext.getContextUnitOfWork().commit();

        return result;
    }
}
