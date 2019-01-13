/**
 *
 */
package com.viasat.wildblue.common.performance;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.service.Service;
import org.apache.cxf.service.invoker.MethodDispatcher;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class PreInvokeStatisticsInterceptor extends AbstractPhaseInterceptor<Message>
{

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PreInvokeStatisticsInterceptor.class);

	private String implClassName;

	/**
	 * @param phase
	 */
	public PreInvokeStatisticsInterceptor()
	{
		super(Phase.PRE_INVOKE);
	}

	/**
	 * @see org.apache.cxf.interceptor.Interceptor#handleMessage(org.apache.cxf.message.Message)
	 */
	@Override
	public void handleMessage(Message message) throws Fault
	{
		Exchange exchange = message.getExchange();
		BindingOperationInfo bop = exchange.get(BindingOperationInfo.class);
		MethodDispatcher md = (MethodDispatcher) exchange.get(Service.class).get(
				MethodDispatcher.class.getName());
		Method method = md.getMethod(bop);

		String declaringClassName = method.getDeclaringClass().getName();

		List<MethodInvocationStatistics> items = MethodInvocationStatistics.getMethodStatistics();
		if (implClassName.equals(declaringClassName))
		{
			// this is first call - external
			// create objects and start accumulation stats
			MethodInvocationStatistics firstStat = new MethodInvocationStatistics(method
					.getDeclaringClass().getSimpleName(), method.getName(),
					System.currentTimeMillis(), null);
			items.add(firstStat);
			MethodInvocationStatistics.getMethodStatistics().add(firstStat);

			// LOGGER.debug("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& call="
			// + declaringClassName + "." + method.getName() +
			// "   Starting Outer");
		}
		else
		{
			// this is first intercept on internal call
			// process stats
			if (items != null && items.size() > 0)
			{
				MethodInvocationStatistics last = items.get(items.size() - 1);
				last.setEndTime(System.currentTimeMillis());
			}
			else
			{
				throw new IllegalStateException(method.getDeclaringClass().getSimpleName() + "."
						+ method.getName() + ": items is null or empty");
			}

			// LOGGER.debug("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& call="
			// + declaringClassName + "." + method.getName() +
			// "   Finishing Inner");
		}
	}

	public void setImplClassName(String implClassName)
	{
		this.implClassName = implClassName;
	}

}
