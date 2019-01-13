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
public class SetupStatisticsInterceptor extends AbstractPhaseInterceptor<Message>
{
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(SetupStatisticsInterceptor.class);
	private String implClassName;

	public SetupStatisticsInterceptor()
	{
		super(Phase.SETUP);
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

		List<MethodInvocationStatistics> items = null;
		items = MethodInvocationStatistics.getMethodStatistics();
		if (items != null && items.size() > 0)
		{
			if (implClassName.equals(declaringClassName))
			{
				// exiting outer method call
				// record end time, print stats, and cleanup
				MethodInvocationStatistics first = items.get(0);
				first.setEndTime(System.currentTimeMillis());

				// LOGGER.debug("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& call="
				// + declaringClassName + "." + method.getName() +
				// "   Finishing Outer");

			}
			else
			{
				// entering inner method call
				// retrieve the list, add item with start time
				MethodInvocationStatistics stat = new MethodInvocationStatistics(method
						.getDeclaringClass().getSimpleName(), method.getName(),
						System.currentTimeMillis(), null);
				items.add(stat);

				// LOGGER.debug("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& call="
				// + declaringClassName + "." + method.getName() +
				// "   Starting Inner");
			}
		}
		else
		{
			throw new IllegalStateException(method.getDeclaringClass().getSimpleName() + "."
					+ method.getName() + ": items is null or empty");
		}
	}

	public void setImplClassName(String implClassName)
	{
		this.implClassName = implClassName;
	}
}
