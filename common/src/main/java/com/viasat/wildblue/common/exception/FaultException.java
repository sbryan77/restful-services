package com.viasat.wildblue.common.exception;

/**
 * This is an empty interface that can be used to point out that a particular
 * exception should be treated like a fault exception. This is particularly for
 * Spring's Custom DataAccessExceptions which must extend from
 * DataAccessException, and therefore cannot extend WildBlueFaultException
 *
 * @deprecated  Use com.viasat.wildblue.common.exception.fault.FaultException
 *              instead.
 */
@Deprecated public interface FaultException
{
    /**
     * This space intentionally left blank
     */
}
