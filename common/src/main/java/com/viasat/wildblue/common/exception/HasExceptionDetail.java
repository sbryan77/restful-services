package com.viasat.wildblue.common.exception;

/**
 * Defines methods required by classes which have exception detail.
 */
public interface HasExceptionDetail
{
    /**
     * @return  exception detail.
     */
    public ExceptionDetail getExceptionDetail();
}
