/**
 *
 */
package com.viasat.wildblue.common.client;

import java.util.concurrent.atomic.AtomicLong;

import com.viasat.wildblue.common.exception.WebServiceException;

/**
 *
 */
public abstract class AbstractWebServiceClient
{
    /** Service locator document name */
    public static final String SERVICE_LOCATOR_DOC_NAME = "SERVICE_LOCATOR";

    /**
     * Default cache reset interval (5 minutes).
     */
    private static long DEFAULT_RESET_INTERVAL = 5 * 60 * 1000;

    /** Last time when cache was reset. */
    private AtomicLong m_lastResetTime = new AtomicLong(0);

    /** Cache reset interval. Unit is milliseconds */
    private AtomicLong m_resetInterval = new AtomicLong(DEFAULT_RESET_INTERVAL);


    protected void checkCacheResetInterval() throws WebServiceException
    {
     // Check if cache has expired
        if (System.currentTimeMillis() - m_lastResetTime.get()
            > m_resetInterval.get())
        {
            synchronized (m_lastResetTime)
            {
                // Check expire again after get the lock, another thread may already updated cache
                if (System.currentTimeMillis() - m_lastResetTime.get()
                    > m_resetInterval.get())
                {
                    resetEndpoint();

                    m_lastResetTime = new AtomicLong(System.currentTimeMillis());

                    initEndpoint();
                }
            }
        }
    }

    protected abstract Object getEndpoint() throws WebServiceException;

    protected abstract void initEndpoint() throws WebServiceException;

    protected abstract void resetEndpoint();

    /**
     * @param resetInterval the resetInterval to set
     */
    public void setResetInterval(Long milliseconds)
    {
        m_resetInterval = new AtomicLong(milliseconds);
    }
}
