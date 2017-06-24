//package com.jovezhao.nest.starter;
//
//import com.jovezhao.nest.ddd.repository.IUnitOfWork;
//import com.jovezhao.nest.exception.CustomException;
//import com.jovezhao.nest.exception.SystemException;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
///**
// * Created by zhaofujun on 2017/6/16.
// */
//@Aspect
//@Component
//public class NestAspect {
//
//
//    @Autowired
//    private IUnitOfWork unitOfWork;
//
//    @Around("execution(public * *(..)) && @within(com.jovezhao.nest.starter.AppService)")
//    public Object aroundMethod(ProceedingJoinPoint joinPoint) {
//
//        Object result = null;
//        try {
//            result = joinPoint.proceed();
//        } catch (CustomException ex) {
//            //业务处理异常，
//            throw ex;
//        } catch (SystemException ex) {
//            //系统异常
//            throw ex;
//        } catch (Throwable ex) {
//            //其它异常以系统异常抛出
//            throw new SystemException("系统异常", ex);
//        }
//
//        unitOfWork.commit();
//
//        return result;
//    }
//}
