package com.zhaofujun.nest.springboot;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.zhaofujun.nest.ddd.context.MethodInvoker;
import com.zhaofujun.nest.ddd.context.ServiceMethodProcesser;
import com.zhaofujun.nest.ddd.context.Transaction;

import java.lang.reflect.Method;

@Component
@Aspect
public class NestAspect {


    @Autowired
    private ApplicationContext applicationContext;

    @Around("execution(public * * (..)) && @within(com.zhaofujun.nest.springboot.AppService)")
    public Object aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodInvoker methodInvoker = new AspectMethodInvoker(joinPoint);
        // 从 appservice 注解中找到配置的事务管理器
        AppService appService = joinPoint.getTarget().getClass().getAnnotation(AppService.class);
        Class transactionClass = appService.transaction();
        Transaction transaction = (Transaction) applicationContext.getBean(transactionClass);
        ServiceMethodProcesser intercept = new ServiceMethodProcesser(methodInvoker, transaction);
        return intercept.doInvoke();
    }

    class AspectMethodInvoker implements MethodInvoker {
        private ProceedingJoinPoint joinPoint;

        public AspectMethodInvoker(ProceedingJoinPoint joinPoint) {
            this.joinPoint = joinPoint;
        }

        @Override
        public String getMethodName() {
            return joinPoint.getSignature().getName();
        }

        @Override
        public Object invoke() throws Throwable {
            return joinPoint.proceed();
        }

        @Override
        public Class getTargetClass() {
            return joinPoint.getTarget().getClass();
        }

        @Override
        public Object getTarget() {
            return joinPoint.getTarget();
        }

        @Override
        public Method getMethod() {
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            return methodSignature.getMethod();
        }
    }
}
