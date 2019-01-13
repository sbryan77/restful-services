package com.viasat.wildblue.common.intercept;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;


/**
 * This class represents an abstract base implementation of a common method
 * interceptor. Spring AOP will be used to wrap the method calls with concrete
 * implementations of this class. Abstract methods declared in this class shall
 * be implemented by each extending class for processing which will execute
 * before, after, and when objects are thrown from the wrapped method. Basic
 * trace level logging of method start/end are provided and should not be
 * implemented in the extending classes.
 */
public abstract class BaseMethodInterceptor implements MethodInterceptor
{
    /**
     * Implementation of the invoke() method called by the Spring AOP layer.
     */
    @Override public Object invoke(MethodInvocation methodInvocation)
        throws Throwable
    {
        Logger log = LoggerFactory.getLogger(methodInvocation.getThis()
                .getClass());

        if (log.isDebugEnabled())
        {
            log.debug("(+)" + methodInvocation.getThis().getClass().getName()
                + "." + methodInvocation.getMethod().getName() + "()");
        }

        long start = System.currentTimeMillis();
        Object result = before(methodInvocation.getMethod(),
                methodInvocation.getArguments(), methodInvocation.getThis());

        long end = start;

        if (result == null)
        {
            try
            {
                // proceed to original method call
                result = methodInvocation.proceed();
                end = System.currentTimeMillis();

                result = afterReturning(result, methodInvocation.getMethod(),
                        methodInvocation.getArguments(),
                        methodInvocation.getThis());
            }
            catch (Throwable t)
            {
                // Common call to alert service, cross-cutting all WB services...
                // AlerServiceAdapter.callAlertService(t);?

                afterThrowing(t);

                end = System.currentTimeMillis();

                if (log.isErrorEnabled())
                {
                    log.error("(x)"

                        + methodInvocation.getThis().getClass().getName() + "."
                        + methodInvocation.getMethod().getName() + "()"
                        + ", RuntimeMillis: " + (end - start) + ", Thrown: "
                        + String.valueOf(t));
                }

                throw t;
            }
        }

        if (log.isTraceEnabled())
        {
            log.trace("(-)" + methodInvocation.getThis().getClass().getName()
                + "." + methodInvocation.getMethod().getName() + "()"
                + ", RuntimeMillis: " + (end - start));
        }

        return result;
    }

    /**
     * Override and put processing code here to run before the service method is
     * invoked.
     *
     * @param  returnValue
     * @param  method
     * @param  args
     * @param  target
     */
    protected Object afterReturning(Object returnValue, Method method,
        Object[] args, Object target) throws Throwable
    {
        return returnValue;
    }

    /**
     * Override and put processing here to run when a Throwable object is thrown
     * from the service method, instead of a normal return. When this method is
     * called, the afterReturning() logic will NOT be run.
     *
     * @param  t
     */
    protected Throwable afterThrowing(Throwable t) throws Throwable
    {
        throw t;
    }

    /**
     * Override and put processing here to run after the service method returns
     * normally. This code will NOT be executed if the service method throws an
     * Exception/Throwable instead of executing a return.
     *
     * @param  method
     * @param  args
     * @param  target
     */
    protected Object before(Method method, Object[] args, Object target)
        throws Throwable
    {
        return null;
    }
}
