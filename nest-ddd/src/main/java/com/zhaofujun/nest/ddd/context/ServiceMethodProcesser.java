package com.zhaofujun.nest.ddd.context;

import com.zhaofujun.nest.exception.CustomException;
import com.zhaofujun.nest.exception.CustomExceptionable;
import com.zhaofujun.nest.exception.SystemException;

/**
 * 服务方法处理器，当执行应用服务方法后会执行本方法
 */
public class ServiceMethodProcesser {
    private MethodInvoker methodInvoker;
    private Transaction transaction;

    public ServiceMethodProcesser(MethodInvoker methodInvoker, Transaction transaction) {
        this.methodInvoker = methodInvoker;
        this.transaction = transaction;
    }

    public Object doInvoke() throws Throwable {

        ServiceContext serviceContext = ServiceContextManager.newServiceContext(methodInvoker);

        Object result = null;

        serviceContext.begin();
        transaction.begin();
        try {
            result = methodInvoker.invoke();
            serviceContext.submit();
        } catch (CustomException | SystemException ex) {
            transaction.rollback();
            throw ex;
        } catch (Throwable ex) {
            transaction.rollback();
            // 其它异常以系统异常抛出
            if (ex instanceof CustomExceptionable) {
                // 业务处理异常
                throw ex;
            } else {
                // 系统处理异常
                throw new SystemException("系统异常", ex);
            }
        } finally {
            serviceContext.end();
            ServiceContextManager.pop();
        }
        transaction.commit();
        return result;

    }
}
