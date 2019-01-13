package com.viasat.wildblue.common.intercept.cxf;

import com.viasat.wildblue.common.util.ThreadLocalMessageUtil;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;


/**
 * The Class ThreadLocalCleanupInterceptor, clears all ThreadLocal values held
 * by {@link ThreadLocalMessageUtil}
 */
@SuppressWarnings("rawtypes")
public class ThreadLocalCleanupInterceptor extends AbstractPhaseInterceptor
{
    /**
     * Instantiates a new thread local cleanup interceptor.
     *
     * @param  phase  the phase
     */
    public ThreadLocalCleanupInterceptor()
    {
        this(Phase.MARSHAL_ENDING);
    }

    /**
     * Instantiates a new thread local cleanup interceptor.
     *
     * @param  phase  the phase
     */
    public ThreadLocalCleanupInterceptor(String phase)
    {
        super(phase);
    }

    /**
     * Instantiates a new thread local cleanup interceptor.
     *
     * @param  phase     the phase
     * @param  uniqueId  the unique id
     */
    public ThreadLocalCleanupInterceptor(String phase, boolean uniqueId)
    {
        super(phase, uniqueId);
    }

    /**
     * Instantiates a new thread local cleanup interceptor.
     *
     * @param  i  the i
     * @param  p  the p
     */
    public ThreadLocalCleanupInterceptor(String i, String p)
    {
        super(i, p);
    }

    /**
     * Instantiates a new thread local cleanup interceptor.
     *
     * @param  i         the i
     * @param  p         the p
     * @param  uniqueId  the unique id
     */
    public ThreadLocalCleanupInterceptor(String i, String p, boolean uniqueId)
    {
        super(i, p, uniqueId);
    }

    /**
     * @see  org.apache.cxf.phase.AbstractPhaseInterceptor#handleFault(org.apache.cxf.message.Message)
     */
    @Override public void handleFault(Message message)
    {
        ThreadLocalMessageUtil.clearAll();
    }

    /**
     * @see  org.apache.cxf.interceptor.Interceptor#handleMessage(org.apache.cxf.message.Message)
     */
    @Override public void handleMessage(Message message) throws Fault
    {
        ThreadLocalMessageUtil.clearAll();
    }
}
