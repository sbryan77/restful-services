package com.viasat.wildblue.common.exception.contingency;

/**
 */
public class AccountExistsException extends WildBlueContingencyException
{
    /** Serial version UID. */
    private static final long serialVersionUID = 1L;

    /**
     */
    public AccountExistsException()
    {
        super();
    }

    /**
     * @param  message
     */
    public AccountExistsException(String message)
    {
        super(message);
    }

    /**
     * @param  cause
     */
    public AccountExistsException(Throwable cause)
    {
        super(cause);
    }

    /**
     * @param  message
     * @param  cause
     */
    public AccountExistsException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
