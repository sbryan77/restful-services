package com.viasat.wildblue.common.exception.contingency;

/**
 * The exception is thrown when trying to create a new Customer but customer
 * already exists.
 */
public class CustomerExistsException extends WildBlueContingencyException
{
    /** Serial version UID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new customer exists exception.
     */
    public CustomerExistsException()
    {
    }

    /**
     * Instantiates a new customer exists exception.
     *
     * @param  message  the message
     */
    public CustomerExistsException(String message)
    {
        super(message);
    }

    /**
     * Instantiates a new customer exists exception.
     *
     * @param  cause  the cause
     */
    public CustomerExistsException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Instantiates a new customer exists exception.
     *
     * @param  message  the message
     * @param  cause    the cause
     */
    public CustomerExistsException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
