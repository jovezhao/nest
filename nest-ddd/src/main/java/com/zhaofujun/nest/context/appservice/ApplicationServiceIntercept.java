package com.zhaofujun.nest.context.appservice;

import com.zhaofujun.nest.NestApplication;
import com.zhaofujun.nest.standard.CustomException;
import com.zhaofujun.nest.standard.CustomExceptionable;
import com.zhaofujun.nest.standard.SystemException;
import com.zhaofujun.nest.standard.SystemExceptionable;

public class ApplicationServiceIntercept {
    private MethodInvoker methodInvoker;
    private TransactionManager transactionManager;

    public ApplicationServiceIntercept(MethodInvoker methodInvoker, TransactionManager transactionManager) {
        this.methodInvoker = methodInvoker;
        this.transactionManager = transactionManager;
    }

    public Object doInvoke() throws Throwable {

        ServiceContext serviceContext = new ServiceContext(methodInvoker.getTargetClass(), methodInvoker.getTarget(), methodInvoker.getMethodName(), transactionManager);
        NestApplication.current().onServiceContextCreated(serviceContext);
        ServiceContextManager.set(serviceContext);
        Object result = null;
        try {
            NestApplication.current().serviceMethodStart(serviceContext);
            result = methodInvoker.invoke();
            NestApplication.current().serviceMethodEnd(serviceContext);

            serviceContext.commit();
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
        }finally {
            ServiceContextManager.pop();
            NestApplication.current().serviceEnd(serviceContext);
        }

        return result;

    }
}

