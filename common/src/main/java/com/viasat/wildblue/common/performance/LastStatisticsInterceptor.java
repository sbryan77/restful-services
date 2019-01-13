/**
 *
 */
package com.viasat.wildblue.common.performance;

import java.util.List;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;

/**
 *
 */
public class LastStatisticsInterceptor extends AbstractPhaseInterceptor<Message>
{
	public LastStatisticsInterceptor()
	{
		super(Phase.POST_INVOKE);
	}

	public LastStatisticsInterceptor(String addAfterClassName)
	{
		super(Phase.POST_INVOKE);
		if (addAfterClassName != null)
		{
			addAfter(addAfterClassName);
		}
	}

	/**
	 * @see org.apache.cxf.interceptor.Interceptor#handleMessage(org.apache.cxf.message.Message)
	 */
	@Override
	public void handleMessage(Message message) throws Fault
	{
		List<MethodInvocationStatistics> items = null;
		items = MethodInvocationStatistics.getMethodStatistics();
		if (items != null && items.size() > 0)
		{
			MethodInvocationStatistics.printStatistics();

			MethodInvocationStatistics.reset();
		}
	}


}
