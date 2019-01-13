package com.viasat.wildblue.common.exception.contingency;

/**
 */
public class InvalidOperationStateException extends WildBlueContingencyException
{
    /** Serial version UID. */
    private static final long serialVersionUID = 1L;

    /**
     */
    public InvalidOperationStateException()
    {
    }

    /**
     * @param  message
     */
    public InvalidOperationStateException(String message)
    {
        super(message);
    }

    /**
     * @param  cause
     */
    public InvalidOperationStateException(Throwable cause)
    {
        super(cause);
    }

    /**
     * @param  message
     * @param  cause
     */
    public InvalidOperationStateException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
