package com.viasat.wildblue.common.exception;

/**
 * This is an empty interface that can be used to point out that a particular
 * exception should be treated like a contingency exception. This is
 * particularly for Spring's Custom DataAccessExceptions which must extend from
 * DataAccessException, and therefore cannot extend WildBlueContingencyException
 *
 * @deprecated  Usecom.viasat.wildblue.common.exception.contingency.ContingencyException
 *              instead.
 */
@Deprecated public interface ContingencyException
{
    /**
     * This space intentionally left blank
     */
}
