package com.viasat.common.applicationstatus.interceptor;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;

public class LastResponseTime extends AbstractPhaseInterceptor<Message>
{
	// making this volatile should ensure synchronicity
	private static volatile long lastResponseTime = 0l;

	public LastResponseTime()
	{
		super(Phase.SEND_ENDING);
	}

	@Override
	public void handleMessage(Message message) throws Fault
	{
		updateLastResponseTime();
	}

	public static long getLastResponseTime()
	{
		return lastResponseTime;
	}

	private static void updateLastResponseTime()
	{
		LastResponseTime.lastResponseTime = System.currentTimeMillis();
	}

}
