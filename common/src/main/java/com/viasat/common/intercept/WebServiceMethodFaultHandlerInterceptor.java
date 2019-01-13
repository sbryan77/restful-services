package com.viasat.common.intercept;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.viasat.common.fault.WebServiceFault;
import com.viasat.common.util.ExceptionUtilities;
import com.viasat.wildblue.common.exception.WebServiceException;
import com.viasat.wildblue.common.intercept.BaseMethodInterceptor;

/**
 * Interceptor for handling Facade's service method calls. It catches/handles
 * unhandled runtime exceptions<br/>
 */
public class WebServiceMethodFaultHandlerInterceptor extends BaseMethodInterceptor implements
		ApplicationContextAware
{
	/** The LOGGER. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(WebServiceMethodFaultHandlerInterceptor.class);
	private ApplicationContext applicationContext = null;

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
	{
		// Assign the ApplicationContext into a static variable
		this.applicationContext = applicationContext;
	}

	/**
	 * After throwing.
	 * 
	 * @param t
	 *            the t
	 * 
	 * @return the throwable
	 * 
	 * @throws Throwable
	 *             the throwable
	 * 
	 * @see com.viasat.wildblue.common.intercept.BaseMethodInterceptor#afterThrowing(java.lang.Throwable)
	 */
	@Override
	protected Throwable afterThrowing(Throwable t) throws Throwable
	{
		// make sure that all exceptions that are thrown from a service method
		// are WebFaultException
		if (!(t instanceof WebServiceFault))
		{
			LOGGER.error(t.getMessage(), t);

			// attempt to parse WebServiceException
			if (t instanceof WebServiceException)
			{
				WebServiceException e = (WebServiceException) t;
				WebServiceFault f = ExceptionUtilities.convert(e);

				super.afterThrowing(f);
			}
			super.afterThrowing(new WebServiceFault(applicationContext, t.getMessage()));
		}

		return super.afterThrowing(t);
	}

	/**
	 * Before.
	 * 
	 * @param method
	 *            the method
	 * @param args
	 *            the args
	 * @param target
	 *            the target
	 * 
	 * @return the object
	 * 
	 * @throws Throwable
	 *             the Throwable
	 * 
	 * @see com.viasat.wildblue.common.intercept.BaseMethodInterceptor#before(java.lang.reflect.Method,
	 *      java.lang.Object[], java.lang.Object)
	 */
	@Override
	protected Object before(Method method, Object[] args, Object target) throws Throwable
	{
		return super.before(method, args, target);
	}
}
