package com.viasat.common.fault;

public class ValidatorToolException extends RuntimeException
{
    /**  */
    private static final long serialVersionUID = 1L;

    public ValidatorToolException(String string)
    {
        super(string);
    }

    public ValidatorToolException(Throwable e)
    {
        super(e);
    }

    public ValidatorToolException(String message, Throwable e)
    {
        super(message, e);
    }
}
