package com.zhaofujun.nest.boot;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.zhaofujun.nest.ddd.ApplicationService;
import com.zhaofujun.nest.ddd.context.MethodInvoker;
import com.zhaofujun.nest.ddd.context.QueryMethodProcessor;
import com.zhaofujun.nest.ddd.context.ServiceMethodProcessor;
import com.zhaofujun.nest.ddd.context.Transaction;

import java.lang.reflect.Method;

/**
 * NestAspect 类
 * 用于处理带有 @AppService 注解的方法的切面逻辑。
 * 主要功能包括事务管理和服务方法的拦截处理。
 */
@Component
@Aspect
public class NestAspect {

    /**
     * Spring 应用上下文
     * 用于获取事务管理器等 Bean
     */
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 
     * 处理带有 @AppService 注解的方法的执行逻辑。
     * 包括事务管理和服务方法的拦截处理。
     *
     * @param joinPoint 切入点
     * @return 方法的返回值
     * @throws Throwable 方法执行过程中可能抛出的异常
     */
    @Around("execution(public * * (..)) && @within(com.zhaofujun.nest.boot.AppService)")
    public Object aroundFromAnnotationForAppService(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodInvoker methodInvoker = new AspectMethodInvoker(joinPoint);
        // 从 appservice 注解中找到配置的事务管理器
        AppService appService = joinPoint.getTarget().getClass().getAnnotation(AppService.class);
        Class transactionClass = appService.transaction();
        Transaction transaction = (Transaction) applicationContext.getBean(transactionClass);
        ServiceMethodProcessor intercept = new ServiceMethodProcessor(methodInvoker, transaction);
        return intercept.doInvoke();
    }

    @Around("execution(public * com.zhaofujun.nest.ddd.ApplicationService+.* (..))")
    public Object aroundFromInterfaceForAppService(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodInvoker methodInvoker = new AspectMethodInvoker(joinPoint);
        var appService = (ApplicationService) joinPoint.getTarget();
        Class transactionClass = appService.getTransactionClass();
        Transaction transaction = (Transaction) applicationContext.getBean(transactionClass);
        ServiceMethodProcessor intercept = new ServiceMethodProcessor(methodInvoker, transaction);
        return intercept.doInvoke();
    }

    @Around("execution(public * com.zhaofujun.nest.ddd.Query+.* (..))")
    public Object aroundFromInterfaceForQuery(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodInvoker methodInvoker = new AspectMethodInvoker(joinPoint);
        QueryMethodProcessor intercept = new QueryMethodProcessor(methodInvoker);
        return intercept.doInvoke();
    }

    /**
     * 内部类 AspectMethodInvoker
     * 实现 MethodInvoker 接口，用于封装 AOP 切入点的相关信息。
     */
    class AspectMethodInvoker implements MethodInvoker {
        private ProceedingJoinPoint joinPoint;

        /**
         * 构造函数
         *
         * @param joinPoint AOP 切入点
         */
        public AspectMethodInvoker(ProceedingJoinPoint joinPoint) {
            this.joinPoint = joinPoint;
        }

        /**
         * 获取方法名称
         *
         * @return 方法名称
         */
        @Override
        public String getMethodName() {
            return joinPoint.getSignature().getName();
        }

        /**
         * 调用目标方法
         *
         * @return 方法的返回值
         * @throws Throwable 方法执行过程中可能抛出的异常
         */
        @Override
        public Object invoke() throws Throwable {
            return joinPoint.proceed();
        }

        /**
         * 获取目标类
         *
         * @return 目标类
         */
        @Override
        public Class getTargetClass() {
            return joinPoint.getTarget().getClass();
        }

        /**
         * 获取目标对象
         *
         * @return 目标对象
         */
        @Override
        public Object getTarget() {
            return joinPoint.getTarget();
        }

        /**
         * 获取方法对象
         *
         * @return 方法对象
         */
        @Override
        public Method getMethod() {
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            return methodSignature.getMethod();
        }
    }
}