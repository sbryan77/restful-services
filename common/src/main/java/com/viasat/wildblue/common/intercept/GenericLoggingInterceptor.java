package com.viasat.wildblue.common.intercept;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.ThrowsAdvice;

import java.lang.reflect.Method;


/**
 * Generic interceptor for logging.</br> This interceptor, tries to retrieve the
 * class logger, on the fly. If the class logger is valid, then before and after
 * a method is invoked, trace messages are logged. Exceptions are logged at info
 * level.
 *
 * @author  ajay
 * @see     http://www.devx.com/Java/Article/30799/0/page/2
 */
public class GenericLoggingInterceptor implements MethodBeforeAdvice,
    AfterReturningAdvice, ThrowsAdvice
{
    static Logger log = null;

    public GenericLoggingInterceptor()
    {
    }

    public void afterReturning(Object arg0, Method arg1, Object[] arg2,
        Object arg3) throws Throwable
    {
        log = LoggerFactory.getLogger(arg3.getClass());

        if (log != null)
        {
            log.trace("(+): " + arg3.getClass().getName() + "." + arg1
                .getName() + "()");
        }
    }

    public void afterThrowing(Method m, Object[] args, Object target,
        Throwable ex)
    {
        log = LoggerFactory.getLogger(target.getClass());

        if (log != null)
        {
            log.error("Exception in method: " + target.getClass().getName()
                + "." + m.getName() + "() Exception is: " + ex.getMessage(),
                ex);
        }
    }

    public void before(Method arg0, Object[] arg1, Object arg2) throws Throwable
    {
        log = LoggerFactory.getLogger(arg2.getClass());

        if (log != null)
        {
            log.trace("(+): " + arg2.getClass().getName() + "." + arg0
                .getName() + "()");
        }
    }
}
